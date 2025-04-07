package cat.deim.asm_32.patinfly.data.datasource.model

import cat.deim.asm_32.patinfly.domain.models.User
import java.util.*

data class UserModel(
    val uuid: String,
    val name: String,
    val email: String,
    val hashedPassword: String,
    val creationDate: Date,
    val lastConnection: Date,
    val deviceId: String
){
    fun toDomain(): User=User(
        uuid=this.uuid,
        name=this.name,
        email=this.email,
        hashedPassword=this.hashedPassword,
        creationDate=this.creationDate,
        lastConnection=this.lastConnection,
        deviceId=this.deviceId
        )

    companion object{
        fun fromDomain(user: User): UserModel=UserModel(
            uuid=user.uuid,
            name=user.name,
            email=user.email,
            hashedPassword=user.hashedPassword,
            creationDate=user.creationDate,
            lastConnection=user.lastConnection,
            deviceId=user.deviceId
        )
    }
}