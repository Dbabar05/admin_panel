package com.gym;

import com.gym.util.TenantContext;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MultiTenancyTests {

    @Test
    void testTenantContextIsolation() {
        java.util.UUID testId = java.util.UUID.randomUUID();
        TenantContext.setCurrentTenant(testId);
        assertThat(TenantContext.getCurrentTenant()).isEqualTo(testId);
        
        TenantContext.clear();
        assertThat(TenantContext.getCurrentTenant()).isNull();
    }
}
