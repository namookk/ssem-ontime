package com.hanssem.ontime.config

import com.hanssem.ontime.utils.WebSupportUtils
import org.springframework.data.domain.AuditorAware
import org.springframework.stereotype.Component
import java.util.*

@Component
class JpaAuditorAware(val webSupportUtils: WebSupportUtils): AuditorAware<Long> {
    override fun getCurrentAuditor(): Optional<Long> {
        val loginAuthDtoOp = webSupportUtils.getLoginAuthDto()
        if(loginAuthDtoOp.isPresent) {
            return loginAuthDtoOp.map{it.id}
        }
        return Optional.of(0)
    }
}