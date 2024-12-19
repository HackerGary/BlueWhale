package com.seecoder.BlueWhale.serviceImpl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.seecoder.BlueWhale.configure.AlipayTools;
import com.seecoder.BlueWhale.enums.OrderStatusEnum;
import com.seecoder.BlueWhale.enums.RoleEnum;
import com.seecoder.BlueWhale.exception.BlueWhaleException;
import com.seecoder.BlueWhale.po.*;
import com.seecoder.BlueWhale.repository.*;
import com.seecoder.BlueWhale.service.CouponService;
import com.seecoder.BlueWhale.service.OrderService;
import com.seecoder.BlueWhale.util.OssUtil;
import com.seecoder.BlueWhale.util.SecurityUtil;
import com.seecoder.BlueWhale.vo.CommentVO;
import com.seecoder.BlueWhale.vo.OrderVO;
import com.seecoder.BlueWhale.vo.StoreVO;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jacoco.agent.rt.internal_43f5073.output.FileOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    SecurityUtil securityUtil;
    @Autowired
    StoreRepository storeRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    OssUtil ossUtil;
    @Autowired
    AlipayTools alipayTools;
    @Autowired
    CouponServiceImpl couponService;
    @Autowired
    CouponRepository couponRepository;
    public void check(OrderVO orderVO)
    {
        if(productRepository.getProductById(orderVO.getProductId())==null||storeRepository.findByStoreId(orderVO.getStoreId())==null|| !userRepository.findById(orderVO.getUserId()).isPresent())
        {
            throw  BlueWhaleException.ErrorParam();
        }
    }
    @Override
    public Integer CreateOrder(OrderVO orderVO) {

        Product product=productRepository.getProductById(orderVO.getProductId());
        if (orderVO.getQuantity() > product.getQuantity()) {
            throw BlueWhaleException.InventoryNotEnough();
        }
        else if(!orderVO.getOrderStatusEnum().equals(OrderStatusEnum.UNPAID))
        {
            throw BlueWhaleException.ErrorStatus();
        }
        Order newOrder=orderVO.toPO();
        newOrder.setTime(new Date());
        newOrder.setProductName(product.getName());

        orderRepository.save(newOrder);
        int quantity=newOrder.getQuantity();
        product.setQuantity(product.getQuantity()-quantity);
        productRepository.save(product);
        return newOrder.getOrderId();
    }

    @Override
    public String PayOrder(Integer orderId,Integer couponId){
        Order order=orderRepository.findByOrderId(orderId);
        Double price=order.getPricesum();
        Double count=0.0;

        if (couponId==-1) {count =price;}
        else {
            count=couponService.previewCoupon(couponId,price);
            Coupon coupon=couponRepository.getCouponByCouponId(couponId);
            coupon.setValid(false);
            couponRepository.save(coupon);
        }
        System.out.println(count);
        System.out.println(order.getOrderId());
        if(order.getOrderStatusEnum()!=OrderStatusEnum.UNPAID)
        {
            throw BlueWhaleException.ErrorStatus();
        }
        else {
            return alipayTools.pay(order.getOrderId().toString(),order.getProductName(),count);
        }
    }

    @Override
    public void PaySuccess(Integer orderId){
        Order order=orderRepository.findByOrderId(orderId);
        order.setOrderStatusEnum(OrderStatusEnum.UNSEND);
        orderRepository.save(order);

    }

    @Override
    public Boolean DeliverOrder(Integer orderId){
        Order order=orderRepository.findByOrderId(orderId);
        if(order.getOrderStatusEnum()!=OrderStatusEnum.UNSEND)
        {
            throw BlueWhaleException.ErrorStatus();
        }
        else {
            order.setOrderStatusEnum(OrderStatusEnum.UNGET);
            orderRepository.save(order);
        }
        return true;
    }

    @Override
    public Boolean ReceiveOrder(Integer orderId){
        Order order=orderRepository.findByOrderId(orderId);
        if(order.getOrderStatusEnum()!=OrderStatusEnum.UNGET)
        {
            throw BlueWhaleException.ErrorStatus();
        }
        else {
            order.setOrderStatusEnum(OrderStatusEnum.UNCOMMENT);
            orderRepository.save(order);
        }
        return true;
    }

    @Override
    public Boolean RefundOrder(Integer orderId){
        Order order=orderRepository.findByOrderId(orderId);
        if(order.getOrderStatusEnum()!=OrderStatusEnum.UNPAID)
        {
            if (alipayTools.refund(order.getOrderId().toString(),order.getPricesum())){
                order.setOrderStatusEnum(OrderStatusEnum.REFUNDED);
                orderRepository.save(order);
                return true;
            }else{
                return false;
            }
        }
        else {
            throw BlueWhaleException.ErrorStatus();
        }
    }

    @Override
    public List<OrderVO> getAllOrder() {
        List<Order> allOrder=orderRepository.findAll();
        List<OrderVO> orderVOList = new ArrayList<>();
        for(int i=0;i<allOrder.size();i++){
            orderVOList.add(allOrder.get(i).toVO());
        }
        return orderVOList;
    }

    @Override
    public List<OrderVO> getOrderByUserId(Integer userId) {
        List<Order> OrderPOList=orderRepository.findByUserId(userId);
        List<OrderVO> orderVOList = new ArrayList<>();
        for(int i=0;i<OrderPOList.size();i++){
            orderVOList.add(OrderPOList.get(i).toVO());
        }
        return orderVOList;
    }

    @Override
    public List<OrderVO> getOrderByStoreId(Integer storeId) {
        List<Order> OrderPOList=orderRepository.findByStoreId(storeId);
        List<OrderVO> orderVOList = new ArrayList<>();
        for(int i=0;i<OrderPOList.size();i++){
            orderVOList.add(OrderPOList.get(i).toVO());
        }
        return orderVOList;
    }

    @Override
    public String getOrderStatement(Integer userId) throws IOException {
        List<Order> orderList;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

        User user = userRepository.findById(userId).get();
        if (user.getRole()== RoleEnum.CEO){
            orderList = orderRepository.findAll();
        }else if (user.getRole()== RoleEnum.STAFF){
            orderList = orderRepository.findByStoreId(user.getStoreId());
        }else{
            throw BlueWhaleException.notPower();
        }
//        // 用于test
//        orderList = orderRepository.findAll();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        Row header = sheet.createRow(0);
        Cell cell0 = header.createCell(0);
        cell0.setCellValue("订单编号");
        Cell cell1 = header.createCell(1);
        cell1.setCellValue("交易时间");
        Cell cell2 = header.createCell(2);
        cell2.setCellValue("卖家店铺");
        Cell cell3 = header.createCell(3);
        cell3.setCellValue("买家ID");
        Cell cell4 = header.createCell(4);
        cell4.setCellValue("商品名称");
        Cell cell5 = header.createCell(5);
        cell5.setCellValue("购买数量");
        Cell cell6 = header.createCell(6);
        cell6.setCellValue("订单金额");


        for (int i = 0;i<orderList.size();i++){
            Order order = orderList.get(i);
            Store store = storeRepository.findByStoreId(order.getStoreId());
            Row row = sheet.createRow(i+1);
            Cell cell00 = row.createCell(0);
            cell00.setCellValue(order.getOrderId());
            Cell cell11 = row.createCell(1);
            cell11.setCellValue(sdf.format(order.getTime()));
            Cell cell22 = row.createCell(2);
            cell22.setCellValue(store.getName());
            Cell cell33 = row.createCell(3);
            cell33.setCellValue(order.getUserId());
            Cell cell44 = row.createCell(4);
            cell44.setCellValue(order.getProductName());
            Cell cell55 = row.createCell(5);
            cell55.setCellValue(order.getQuantity());
            Cell cell66 = row.createCell(6);
            cell66.setCellValue(order.getPricesum());
        }

        File file = new File("C:","BlueWhaleFiles");
        file.mkdir();
        Date date = new Date();
        String name = sdf.format(date)+".xlsx";
        FileOutputStream outputStream = new FileOutputStream("C:/BlueWhaleFiles/"+name);
        workbook.write(outputStream);
        outputStream.close();
        return ossUtil.upload(name,(InputStream) new FileInputStream("C:/BlueWhaleFiles/"+name));
    }

}
