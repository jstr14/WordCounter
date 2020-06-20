package com.hector.wordcounter.domain.usecase

import android.net.Uri
import com.hector.wordcounter.domain.Interactor
import com.hector.wordcounter.domain.model.Document
import com.hector.wordcounter.domain.repository.DocumentRepository
import com.hector.wordcounter.domain.Result
import javax.inject.Inject

class GetFilesFromUriUseCase @Inject constructor(private val repository: DocumentRepository) :
    Interactor<Collection<Document>, GetFilesFromUriUseCase.Parameters>() {


    override fun run(params: Parameters): Result<Collection<Document>, *> {
        return repository.getFilesFromUri(params.uri)
    }

    data class Parameters(val uri: Uri)

}