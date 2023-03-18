package com.techexpert.ezdine.app.main

import com.techexpert.ezdine.app.foundation.base.BaseViewModel
import com.techexpert.ezdine.app.foundation.network.repository.BaseRepository
import com.techexpert.ezdine.app.util.AppPreference
import com.techexpert.ezdine.app.util.ResourcesProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val baseRepository: BaseRepository,
    private val resourcesProvider: ResourcesProvider,
    private val appPreference: AppPreference
) : BaseViewModel()
