package com.project.sample.api.demo.web.service;

import com.project.sample.api.demo.domain.entity.Assets;
import com.project.sample.api.demo.domain.entity.User;
import com.project.sample.api.demo.domain.repository.AssetsRepository;
import com.project.sample.api.demo.domain.type.AssetsType;
import com.project.sample.api.demo.exception.ExceptionCode;
import com.project.sample.api.demo.web.model.assets.AssetsAddResponse;
import com.project.sample.api.demo.web.model.assets.AssetsListResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
class AssetsServiceImplTest {

    @Mock
    AssetsRepository assetsRepository;

    @InjectMocks
    AssetsServiceImpl assetsService;

    @BeforeEach
    void setUp() {
        Assets assets = Assets.builder().assetsType(AssetsType.COIN).amount(10000).build();
//        when(assetsRepository.findById(anyLong())).thenReturn(Optional.of(assets));
        when(assetsRepository.save(any(Assets.class))).thenReturn(Assets.builder().id(1L).assetsType(AssetsType.COIN).amount(10000).build());
    }

    @Test
    @DisplayName("자산을 추가한다.")
    void addAssets() {
        AssetsAddResponse response = assetsService.addAssets(User.builder().id(1L).build(), AssetsType.COIN, 10000);
        assertThat(response.getCode()).isEqualTo(ExceptionCode.SUCCESS.getCode());
    }

    @Test
    @DisplayName("자산목록을 요청한다.")
    void getAssetsList() {
        AssetsListResponse response = assetsService.getAssetsList(User.builder().build());
        assertThat(response.getCode()).isEqualTo(ExceptionCode.SUCCESS.getCode());
        assertThat(response.getAssets().size() == 1);
    }

}