package kaidev.tracker.repository

import kaidev.tracker.model.TrackedDay
import org.springframework.data.mongodb.repository.MongoRepository

interface TrackedDayRepository : MongoRepository<TrackedDay?, String?> {
    fun findById(id: String?): TrackedDay?
    fun findByUserId(userId: String?): List<TrackedDay?>?
    fun findByUserIdAndDate(userId: String?, date: String?): TrackedDay?
}
