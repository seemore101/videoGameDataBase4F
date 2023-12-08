package com.example.videoGameDataBase.data.local.model

import com.example.videoGameDataBase.common.DBModelDetails
import com.example.videoGameDataBase.common.UIModelDetails
import com.example.videoGameDataBase.common.UIModelListing


fun UIModelDetails.toDBModelDetails():DBModelDetails{
        return DBModelDetails(
            this.id,
            this.name,
            this.description,
            this.metacritic,
            this.releaseDate,
            this.websiteURL,
            this.redditURL,
            this.genres,
            this.developers,
            this.backgroundImage
        )
    }

fun DBModelDetails.toUIModelDetails():UIModelDetails{
    return UIModelDetails(
        this.id,
        this.name,
        this.description,
        this.metacritic,
        this.releaseDate,
        this.websiteURL,
        this.redditURL,
        ArrayList(this.genres),
        ArrayList(this.developers),
        this.backgroundImage
    )
}

fun DBModelDetails.toUIModelListing():UIModelListing{
    return UIModelListing(
        this.id,
        this.name,
        this.backgroundImage
    )
}

