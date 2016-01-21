package service;

import entities.Product;
import entities.VaultEntity;
import entities.abs.PersistenceEntity;
import mock.ESBMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import util.EntityConverter;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;

/**
 * Created by alex on 1/21/16.
 */
@RunWith(PowerMockRunner.class)
public class DataVaultServiceTest {

    private ESBMock jaMaarIkMockJouToch;
    private DataVaultService vaultService;
    private EntityConverter mockConverter;

    @Before
    public void setUp() throws Exception {
        jaMaarIkMockJouToch = Mockito.mock(ESBMock.class);
        vaultService = new DataVaultService();
        vaultService.setEsb(jaMaarIkMockJouToch);
    }

    @PrepareForTest(EntityConverter.class)
    @Test
    public void testPostToVault() throws Exception {
        PowerMockito.mockStatic(EntityConverter.class);
        PowerMockito.when(EntityConverter.convert(any(PersistenceEntity.class))).thenReturn(new VaultEntity("success"));
        Mockito.when(jaMaarIkMockJouToch.post(any(VaultEntity.class))).thenReturn(true);
        vaultService.postToVault(new Product());
        assertThat(vaultService.getBlockingQueue().isEmpty(), is(true));

        Mockito.when(jaMaarIkMockJouToch.post(any(VaultEntity.class))).thenReturn(false);
        vaultService.postToVault(new Product());
        assertThat(vaultService.getBlockingQueue().isEmpty(), is(false));
    }

    @Test
    public void testRetry() throws Exception {
        Mockito.when(jaMaarIkMockJouToch.post(any(VaultEntity.class))).thenReturn(true);
        vaultService.getBlockingQueue().add(new VaultEntity(""));
        vaultService.retry();
        assertThat(vaultService.getBlockingQueue().isEmpty(), is(true));

        Mockito.when(jaMaarIkMockJouToch.post(any(VaultEntity.class))).thenReturn(false);
        vaultService.getBlockingQueue().add(new VaultEntity(""));
        vaultService.retry();
        assertThat(vaultService.getBlockingQueue().isEmpty(), is(false));

        Mockito.when(jaMaarIkMockJouToch.post(any(VaultEntity.class))).thenReturn(false);
        vaultService.getBlockingQueue().clear();
        vaultService.retry();
        assertThat(vaultService.getBlockingQueue().isEmpty(), is(true));
    }
}