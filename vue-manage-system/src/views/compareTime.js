//输入的时间是'2022/3/11 12:00'这种格式
export const compareTime = (gettime) => {
    var today = new Date()  //获取当前时间
    gettime = gettime.replace(/-/g, '/');
    //转化成时间戳作比较
    var endTime = new Date(gettime) //自己的时间
    console.log(today.getTime())
    console.log(endTime.getTime())
    if (today.getTime() > endTime.getTime()) {
        //当前时间大于我的时间
        return false
    } else {
        //当前时间小于我的时间
        return true
    }
}