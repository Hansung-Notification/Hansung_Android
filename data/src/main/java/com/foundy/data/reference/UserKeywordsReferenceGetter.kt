package com.foundy.data.reference

import com.foundy.data.constant.FirebaseConstant
import com.foundy.domain.exception.NotSignedInException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

/**
 * 사용자의 키워드 목록의 Database reference를 반환하는 객체이다.
 *
 * 반드시 로그인이 된 상태에서 [invoke]를 호출해야한다.
 */
class UserKeywordsReferenceGetter : DatabaseReferenceGetter {
    override fun invoke(): DatabaseReference {
        val uid = Firebase.auth.uid ?: throw NotSignedInException()
        return Firebase.database.reference
            .child(FirebaseConstant.USERS)
            .child(uid)
            .child(FirebaseConstant.KEYWORDS)
    }
}
