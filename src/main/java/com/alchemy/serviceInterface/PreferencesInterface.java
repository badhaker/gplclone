package com.alchemy.serviceInterface;

import com.alchemy.dto.PreferencesDto;

public interface PreferencesInterface {

	PreferencesDto addPreferences(PreferencesDto preferencesDto, Long userId);

}
