package com.loanapp.apiservice

/**
 * Created by bingju on 2017/7/1.
 * 一场春雨一场暖，一场秋雨一场寒，春雨润物无声，秋雨雷如尿崩。如果春雨是南方的姑娘温柔似水，那么秋雨就是北方的
 * 汉子豁达豪爽。特别是北方的秋雨，雷声滚滚后必有大雨，就像北方爷们，在唇枪舌剑之后必有拳打脚踢。
 *
 *  雷雨为这个夏天画上了逗号，然后温度依是夏天的温度，可天气的确大不一样，空气中少了几
 *  分燥热，多了一丝凉爽。姑娘们的短裤虽然依旧很短，但是明显比男生的内裤长了。太阳公公
 *  这个变态的老头也露出了久违的慈祥笑容了。
 *
 */
class HttpModel: HttpModelBase() {
    /**
     * 用户登录
     * */
//    fun login(map: Map<String,String>,subscriber: Subscriber<LoginEntity>) {
//        var maps = getMap(map)
//        setModel(apiServiceNoToken.loginserver(maps),subscriber)
//    }
//    /**
//     * 用户注册
//     * */
//    fun registerUser(map: Map<String,String>,subscriber: Subscriber<ModelEntity>) {
//        var maps = getMap(map)
//        setModel(apiServiceNoToken.registerUser(maps),subscriber)
//    }
//    /**
//     * 重置密码
//     * */
//    fun resetPassword(map: Map<String,String>,subscriber: Subscriber<ModelEntity>) {
//        var maps = getMap(map)
//        setModel(apiServiceNoToken.resetPassword(maps),subscriber)
//    }
//    /**
//     * 短信发送
//     * @param sendType
//     *      1：注册；2：修改密码
//     * */
//    fun sendShortMessage(mobilePhone: String,sendType: String,subscriber: Subscriber<ModelEntity>){
//        setModel(apiServiceNoToken.sendShortMessage(mobilePhone,sendType),subscriber)
//    }
//    /**
//     * 用户信息
//     * */
//    fun getPersonInfo(subscriber: Subscriber<PersonalEntity>) {
////        var maps = getMap(map)
//        setModel(apiSerivce.getPersonInfo(),subscriber)
//    }
//    /**
//     * 更新用户信息
//     */
//    fun updatePersonInfo(map: Map<String,String>,subscriber: Subscriber<ModelEntity>) {
//        val maps = getMap(map)
//        setModel(apiSerivce.updatePersonInfo(maps),subscriber)
//    }
//    /**
//     * 贷款列表
//     * */
//    fun getLoanList(subscriber: Subscriber<LoadEntity>) {
////        var maps = getMap(map)
//        setModel(apiSerivce.getLoanList(),subscriber)
//    }
//
//    /**
//     * 银行列表
//     * */
//    fun getBankList(subscriber: Subscriber<BankEntity>) {
////        var maps = getMap(map)
//        setModel(apiSerivce.getBankList(),subscriber)
//    }
//    /**
//     * 个人信息
//     * */
//    fun getPersonForLoan(subscriber: Subscriber<PersonalLoanEntity>) {
//        val maps = getMap(null)
//        setModel(apiSerivce.getPersonForLoan(maps),subscriber)
//    }
//    /**
//     * 更新个人信息
//     */
//    fun updatePersonForLoan(map: Map<String,String>,subscriber: Subscriber<ModelEntity>) {
//        val maps = getMap(map)
//        setModel(apiSerivce.updatePersonForLoan(maps),subscriber)
//    }
//    /**
//     * 用户企业信息
//     * */
//    fun getProfession(subscriber: Subscriber<ProfessionEntity>) {
//        val maps = getMap(null)
//        setModel(apiSerivce.getProfession(maps),subscriber)
//    }
//    /**
//     * 更新用户企业信息
//     */
//    fun updateProfession(map: Map<String,String>,subscriber: Subscriber<ModelEntity>) {
//        val maps = getMap(map)
//        setModel(apiSerivce.updateProfession(maps),subscriber)
//    }
//    /**
//     * 修改头像
//     * */
//    fun changeIconHead(map: Map<String, RequestBody>,subscriber: Subscriber<ModelEntity>){
//
//        setModel(apiSerivce.changeIconHead(map),subscriber)
////        setModel(apiSerivceUserCodeTest.changeIconHead(map),subscriber)
//    }
//
//    /**
//     * 意见反馈
//     * */
//    fun feedback(map: Map<String,String>,subscriber: Subscriber<ModelEntity>) {
//        var maps = getMap(map)
//        setModel(apiSerivce.feedback(maps),subscriber)
//    }
//    /**------------内部员工接口------------------*/
//    /**
//     * 用户登录
//     */
//    fun innerloginserver(map: Map<String,String>,subscriber: Subscriber<InnerLoginEntity>){
//        val maps = getMap(map)
//        setModel(apiSerivceUserCode.innerloginserver(maps),subscriber)
//    }
//    /**
//     * 贷款审批待办列表
//     * @Query("userCode") userCode: String,
//    @Query("userRole") userRole: String,
//    @Query("userBranch") userBranch: String
//     * */
//    fun getBpmListForLoan(userCode:String,userRole: String,userBranch: String,subscriber: Subscriber<LoanBpmEntity>){
////        var maps = getMap(map)
//        setModel(apiSerivceUserCode.getBpmListForLoan(userCode,userRole,userBranch),subscriber)
//    }
//    /**
//     * 额度审批待办列表
//     * */
//    fun getBpmListForLine(userCode:String,userRole: String,userBranch: String,subscriber: Subscriber<LineBpmEntity>){
////        var maps = getMap(map)
//        setModel(apiSerivceUserCode.getBpmListForLine(userCode,userRole,userBranch),subscriber)
//    }
//    /**
//     * 单笔审批详情
//     * */
//    fun getApproveInfo(userCode:String,userRole: String,userBranch: String,appID: String,subscriber: Subscriber<ApproveInfo>){
//        setModel(apiSerivceUserCode.getApproveInfo(userCode,userRole,userBranch,appID),subscriber)
//    }
//    /**
//     * 提交审批
//     * */
//    fun submitFlow(map: Map<String,String>,subscriber: Subscriber<ModelEntity>){
//        var maps = getMap(map)
//        setModel(apiSerivceUserCode.submitFlow(maps),subscriber)
//    }
//    /**
//     * 保存审批意见
//     * */
//    fun saveApproveInfo(map: Map<String,String>,subscriber: Subscriber<ModelEntity>){
//        var maps = getMap(map)
//        setModel(apiSerivceUserCode.saveApproveInfo(maps),subscriber)
//    }
//    /**
//     * 路由列表
//     * */
//    fun chooseFlowRoute(userCode:String,userRole: String,userBranch: String,appID: String,subscriber: Subscriber<RouteInfo>){
//        setModel(apiSerivceUserCode.chooseFlowRoute(userCode,userRole,userBranch,appID),subscriber)
//    }
//    /**
//     * 上传文件
//     */
//    fun uploadFiles(imageList: Map<String,RequestBody>,subscriber: Subscriber<ModelEntity>) {
//        setModel(apiSerivceUserCode.uploadFiles(imageList),subscriber)
//    }
//    /**
//     * 上传文件
//     */
//    fun mobileUserUploadFiles(fileItem:String,copyFlag:String,fileAppendDesc:String,imageList: Map<String,RequestBody>,subscriber: Subscriber<ModelEntity>) {
//        setModel(apiSerivce.mobileUserUploadFiles(fileItem,copyFlag,fileAppendDesc,imageList),subscriber)
//    }
//    /**
//     *
//     */
//    fun findMobileUserFileList(subscriber: Subscriber<UserFileInfo>) {
//        setModel(apiSerivce.findMobileUserFileList(),subscriber)
//    }
}