package com.duorebate.model
/**
 * @author Administrator
 * @date 2018/9/25
 */
/*base Entity*/
open class BaseEntity(open val resCode: String, open val resDesc: String)

data class ModelEntity(override val resCode: String,override val resDesc: String): BaseEntity(resCode,resDesc)

data class LoginEntity(val re: String, override val resCode: String,override val resDesc: String): BaseEntity(resCode, resDesc)