package com.foundy.data.reference

import com.foundy.data.constant.FirebaseConstant
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class KeywordsReferenceGetter : DatabaseReferenceGetter {
    override fun invoke(): DatabaseReference {
        return Firebase.database.reference.child(FirebaseConstant.KEYWORDS)
    }
}
