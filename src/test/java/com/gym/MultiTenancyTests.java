package com.gym;

import com.gym.util.TenantContext;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MultiTenancyTests {

    @Test
    void testTenantContextIsolation() {
        TenantContext.setCurrentTenant(1L);
        assertThat(TenantContext.getCurrentTenant()).isEqualTo(1L);
        
        TenantContext.clear();
        assertThat(TenantContext.getCurrentTenant()).isNull();
    }
}
