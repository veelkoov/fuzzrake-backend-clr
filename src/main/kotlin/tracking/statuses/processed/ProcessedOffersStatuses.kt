package tracking.statuses.processed

import tracking.creator.Creator

data class ProcessedOffersStatuses(
    val creator: Creator,
    val items: List<ProcessedOfferStatus>,
    val issues: Boolean,
)