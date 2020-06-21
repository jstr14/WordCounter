package com.hector.wordcounter.domain.usecase

import android.net.Uri
import com.hector.wordcounter.domain.Interactor
import com.hector.wordcounter.domain.Result
import com.hector.wordcounter.domain.model.DocumentsFolder
import com.hector.wordcounter.domain.repository.DocumentRepository
import javax.inject.Inject

class GetFilesFromUriUseCase @Inject constructor(private val repository: DocumentRepository) :
    Interactor<DocumentsFolder, GetFilesFromUriUseCase.Parameters>() {


    override fun run(params: Parameters): Result<DocumentsFolder, *> {
        return repository.getFilesFromUri(params.uri)
    }

    data class Parameters(val uri: Uri)

}