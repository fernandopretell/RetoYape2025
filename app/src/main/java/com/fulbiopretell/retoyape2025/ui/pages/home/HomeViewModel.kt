package com.fulbiopretell.retoyape2025.ui.pages.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fulbiopretell.retoyape2025.core.Resource
import com.fulbiopretell.retoyape2025.core.ResultType
import com.fulbiopretell.retoyape2025.core.toMessage
import com.fulbiopretell.retoyape2025.data.IRepositoryApp
import com.fulbiopretell.retoyape2025.mappers.toListModelRecipe
import com.fulbiopretell.retoyape2025.ui.models.Recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class HomeViewModel @Inject constructor(
    private val repositoryApp: IRepositoryApp
) : ViewModel()  {

    private val _recipes = MutableStateFlow<Resource<List<Recipe>>>(Resource.Idle)
    val recipes = _recipes.asStateFlow()

    init {
        fetchRecipes()
    }

    fun fetchRecipes() {

        viewModelScope.launch(Dispatchers.IO) {
            _recipes.value = Resource.Loading
            when (val result = repositoryApp.getRecipes()) {
                is ResultType.Error -> {
                    _recipes.value = Resource.Error(result.error.toMessage())
                }

                is ResultType.Success -> {
                    val recipes = result.data
                    _recipes.value = Resource.Success(recipes.toListModelRecipe())
                }
            }
        }
    }
}
