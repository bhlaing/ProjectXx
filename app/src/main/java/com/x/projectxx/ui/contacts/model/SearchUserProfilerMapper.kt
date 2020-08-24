package com.x.projectxx.ui.contacts.model

import com.x.projectxx.domain.user.model.ContactStatus
import com.x.projectxx.domain.user.model.User
//
//private fun mapUserRelationshipStatus(status: ContactStatus,
//                                      isContact: Boolean): RelationshipStatus = if(isContact) {
//                                          when(status) {
//                                              ContactStatus.REQUEST -> RelationshipStatus.PENDING
//                                              ContactStatus.PENDING -> RelationshipStatus.PENDING
//                                              ContactStatus.CONFIRMED -> RelationshipStatus.CONFIRMED
//                                          }
//                                      } else {
//                                          RelationshipStatus.NONE
//                                      }
//
//
//private fun mapToSearchUserProfileItem(profile: User, isContact: Boolean) = profile.let {
//    SearchUserProfileItem(it.userId,
//    it.displayName,
//    it.image,
//    it.email,
//    it.status,
//        mapUserRelationshipStatus(isContact),
//    1)
//
//}



//PENDING,
//CONFIRMED,
//REQUEST,
//NONE

//class SearchUserProfileItem (
//    val userId: String,
//    val displayName: String?,
//    val image: String?,
//    val email: String?,
//    val status: String = "Life is good!",
//    val relationship: RelationshipStatus,
//    val relationshipIcon: Int