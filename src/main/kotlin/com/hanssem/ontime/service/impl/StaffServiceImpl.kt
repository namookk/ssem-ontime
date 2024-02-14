package com.hanssem.ontime.service.impl

import com.hanssem.ontime.payload.domain.Staff
import com.hanssem.ontime.payload.dto.AuthDto
import com.hanssem.ontime.repository.StaffRepository
import com.hanssem.ontime.service.StaffService
import org.springframework.stereotype.Service

@Service
class StaffServiceImpl(
        private val staffRepository: StaffRepository
): StaffService {

    override fun getOrCreateStaff(googleDto: AuthDto.GoogleLoginDto): Staff {
        val staffOp = staffRepository.findBySocialKey(googleDto.socialKey)
        if(staffOp.isPresent) {
           return staffOp.get()
        }
        return createStaff(googleDto.socialKey, googleDto.email, googleDto.name)
    }

    private fun createStaff(socialKey:String, email:String, name:String): Staff {
        val staff = Staff(
                socialKey = socialKey,
                email = email,
                name = name
        )
        return staffRepository.save(staff)
    }
}