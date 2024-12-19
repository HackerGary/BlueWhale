//将身份转化为中文显示

export function parseRole(role: string | null) {
    if (role === 'MANAGER') {
        return "管理员"
    } else if (role === 'CUSTOMER') {
        return "顾客"
    } else if (role === 'STAFF') {
        return "商家"
    } else if (role === 'CEO') {
        return "CEO"
    }
}

//将时间转化为日常方式
export function parseTime(time: string) {
    let times = time.split(/T|\./)
    return times[0] + "  " + times[1]
}

//收获方式转化为中文
export function parsePickType(pickGoodType: string | null) {
    if (pickGoodType === 'DELIVERY') {
        return "快递送达"
    } else if (pickGoodType === 'PICKUP') {
        return "到店自提"
    }else{
        return "尚未决定"
    }
}

//将订单状态转化为中文
export function parseOrderStatus(orderStatusEnum: string | null){
    if (orderStatusEnum === 'UNPAID'){
        return "待支付"
    }else if (orderStatusEnum === 'UNSEND'){
        return "待发货"
    }else if (orderStatusEnum === 'UNGET'){
        return "待收货"
    }else if (orderStatusEnum === 'UNCOMMENT'){
        return "待评价"
    }else if (orderStatusEnum === 'DONE'){
        return "已完成"
    }else if (orderStatusEnum === 'REFUNDED'){
        return "已退款"
    }
}


//将物品类别转化为中文显示
export function parseType(role: string | null) {
    if (role === 'FOOD') {
        return "食物"
    } else if (role === 'CLOTHES') {
        return "服饰"
    } else if (role === 'FURNITURE') {
        return "家具"
    } else if (role === 'ELECTRONICS') {
        return "电子产品"
    } else if (role === 'ENTERTAINMENT') {
        return "娱乐"
    } else if (role === 'SPORTS') {
        return "体育产品"
    } else if (role === 'LUXURY') {
        return "奢侈品"
    }
}

//将优惠券类型转化为中文显示
export function parseCouponTypeName(role: string | null){
    if (role === 'SPECIAL'){
        return "蓝鲸券"
    }else if (role === 'FULL_REDUCTION'){
        return "满减券"
    }
}

export function parsePassword(password: string) {
    var s = ""

    for (var i = 0; i < password.length; i++) {
        s += (password[i]+5)
    }
    return s
}