package com.loanapp.apiservice

/**
 * Created by bingju on 2017/7/1.
 */
interface ApiSerivce {
    /**
     * 用户登录
     */
//    @FormUrlEncoded
//    @POST("/soa/users/MobileUserService/login")
//    fun loginserver(@FieldMap fields: Map<String,String>): Observable<LoginEntity>
////    fun loginserver(@HeaderMap headerMap: Map<String,String>,@Field("pwd") pwd: String): Observable<LoginEntity>
//    /**
//     * 用户注册
//     * */
//    @FormUrlEncoded
//    @POST("/soa/users/MobileUserService/registerUser")
//    fun registerUser(@FieldMap fields: Map<String,String>): Observable<ModelEntity>
//    /**
//     * 重置密码
//     * */
//    @FormUrlEncoded
//    @POST("/soa/users/MobileUserService/resetPassword")
//    fun resetPassword(@FieldMap fields: Map<String,String>): Observable<ModelEntity>
//    /**
//     * TODO 短信发送
//     * */
//    @GET("/soa/users/MobileUserService/sendShortMessage")
//    fun sendShortMessage(@Query("mobilePhone") mobilePhone: String,
//                         @Query("sendType") sendType: String): Observable<ModelEntity>
//    /**
//     * 用户信息
//     * */
////    @FormUrlEncoded
//    @GET("/soa/users/MobileUserService/getPersonInfo")
//    fun getPersonInfo():Observable<PersonalEntity>
//    /**
//     * 更新用户信息
//     */
//    @FormUrlEncoded
//    @POST("/soa/users/MobileUserService/updatePersonInfo")
//    fun updatePersonInfo(@FieldMap fields: Map<String,String>):Observable<ModelEntity>
//    /**
//     *贷款列表
//     * */
////    @FormUrlEncoded
//    @GET("/soa/users/MobileUserService/getLoanList")
//    fun getLoanList(): Observable<LoadEntity>
////    fun getLoanList(@HeaderMap headerMap: Map<String,String>): Observable<LoadEntity>
//
//    /**
//     *银行列表
//     * */
////    @FormUrlEncoded
//    @GET("/soa/users/MobileUserService/getBankList")
//    fun getBankList(): Observable<BankEntity>
//
//    /**
//     *个人信息
//     * */
////    @FormUrlEncoded
//    @GET("/soa/users/MobileUserService/getPersonForLoan")
//    fun getPersonForLoan(@HeaderMap headerMap: Map<String,String>): Observable<PersonalLoanEntity>
//
//    /**
//     * 更新个人信息
//     */
//    @FormUrlEncoded
//    @POST("/soa/users/MobileUserService/updatePersonForLoan")
//    fun updatePersonForLoan(@FieldMap fields: Map<String,String>):Observable<ModelEntity>
//    /**
//     *用户企业信息
//     * */
////    @FormUrlEncoded
//    @GET("/soa/users/MobileUserService/getProfession")
//    fun getProfession(@HeaderMap headerMap: Map<String,String>): Observable<ProfessionEntity>
//    /**
//     * 更新用户企业信息
//     */
//    @FormUrlEncoded
//    @POST("/soa/users/MobileUserService/updateProfession")
//    fun updateProfession(@FieldMap fields: Map<String,String>):Observable<ModelEntity>
//    /**
//     * 修改头像
//     * */
//    @Multipart
//    @POST("/soa/users/MobileUserService/changeIconHead")
////    @POST("/repairapi/api/user/modifyIconHead")
//    fun changeIconHead(@PartMap fields: Map<String, @JvmSuppressWildcards RequestBody>): Observable<ModelEntity>
//
//    /**
//     * 重置密码
//     * */
//    @FormUrlEncoded
//    @POST("/soa/users/MobileUserService/feedback")
//    fun feedback(@FieldMap fields: Map<String,String>): Observable<ModelEntity>
//    /**
//     * uploadFiles
//     */
//    @Multipart
//    @POST("/soa/users/MobileUserService/mobileUserUploadFiles")
////    fun mobileUserUploadFiles(@Query("fileItem") fileItem:String,@Query("copyFlag") copyFlag:String,@Query("fileAppendDesc") fileAppendDesc:String,@PartMap imageList:Map<String, @JvmSuppressWildcards RequestBody>): Observable<ModelEntity>
//    fun mobileUserUploadFiles(@Query("fileItem") fileItem:String,@Query("copyFlag") copyFlag:String,@Query("fileAppendDesc") fileAppendDesc:String,@PartMap imageList:Map<String, @JvmSuppressWildcards RequestBody>): Observable<ModelEntity>
//    /**
//     * findMobileUserFileList
//     * */
//    @GET("/soa/users/MobileUserService/findMobileUserFileList")
//    fun findMobileUserFileList(): Observable<UserFileInfo>
//    /**------------内部员工接口------------------*/
//    /**
//     * 用户登录
//     */
//    @FormUrlEncoded
//    @POST("/soa/users/UserService/login")
//    fun innerloginserver(@FieldMap fields: Map<String,String>): Observable<InnerLoginEntity>
//
//    /**
//     * 贷款审批待办列表
//     * */
////    @FormUrlEncoded
//    @GET("/soa/loan/LoansService/getBpmListForLoan")
//    fun getBpmListForLoan(@Query("userCode") userCode: String,
//                          @Query("userRole") userRole: String,
//                          @Query("userBranch") userBranch: String): Observable<LoanBpmEntity>
//    /**
//     * 额度审批待办列表
//     * */
////    @FormUrlEncoded
//    @GET("/soa/loan/LoansService/getBpmListForLine")
//    fun getBpmListForLine(@Query("userCode") userCode: String,
//                          @Query("userRole") userRole: String,
//                          @Query("userBranch") userBranch: String): Observable<LineBpmEntity>
//    /**
//     * 单笔审批详情
//     * */
//    //    @FormUrlEncoded
//    @GET("/soa/loan/LoansService/getApproveInfo")
//    fun getApproveInfo(@Query("userCode") userCode: String,
//                      @Query("userRole") userRole: String,
//                      @Query("userBranch") userBranch: String,
//                       @Query("appID") appID: String): Observable<ApproveInfo>
//    /**
//     * 提交审批
//     */
//    @FormUrlEncoded
//    @POST("/soa/loan/LoansService/submitFlow")
//    fun submitFlow(@FieldMap fields: Map<String,String>): Observable<ModelEntity>
//    /**
//     * 保存审批意见
//     */
//    @FormUrlEncoded
//    @POST("/soa/loan/LoansService/saveApproveInfo")
//    fun saveApproveInfo(@FieldMap fields: Map<String,String>): Observable<ModelEntity>
//    /**
//     * 路由列表
//     * */
//    //    @FormUrlEncoded
//    @GET("/soa/loan/LoansService/chooseFlowRoute")
//    fun chooseFlowRoute(@Query("userCode") userCode: String,
//                       @Query("userRole") userRole: String,
//                       @Query("userBranch") userBranch: String,
//                       @Query("appID") appID: String): Observable<RouteInfo>
//    /**
//     * uploadFiles
//     */
//    @Multipart
//    @POST("/soa/users/UserService/uploadFiles")
//    fun uploadFiles(@PartMap imageList:Map<String, @JvmSuppressWildcards RequestBody>): Observable<ModelEntity>
}