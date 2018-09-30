package repositories.dto

import repositories.domain.Repository

class GithubAnswerDto(val total_count: Long,
                      val isIncomplete_results: Boolean,
                      val items: List<Repository>)