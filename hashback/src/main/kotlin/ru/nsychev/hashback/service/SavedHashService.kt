package ru.nsychev.hashback.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.nsychev.hashback.model.SavedHash
import ru.nsychev.hashback.repo.SavedHashRepository

@Service
class SavedHashService {
    @Autowired
    private lateinit var savedHashRepository: SavedHashRepository

    fun save(savedHash: SavedHash): SavedHash =
        savedHashRepository.save(savedHash)

    fun retrieve(hash: String) =
        savedHashRepository.findByHash(hash)
}
