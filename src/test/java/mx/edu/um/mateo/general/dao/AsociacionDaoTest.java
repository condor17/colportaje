/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.edu.um.mateo.general.dao;

import java.util.*;
import mx.edu.um.mateo.Constantes;
import mx.edu.um.mateo.general.model.Asociacion;
import mx.edu.um.mateo.general.model.Rol;
import mx.edu.um.mateo.general.model.Union;
import mx.edu.um.mateo.general.model.Usuario;
import mx.edu.um.mateo.general.test.BaseTest;
import mx.edu.um.mateo.general.utils.UltimoException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author gibrandemetrioo
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:mateo.xml", "classpath:security.xml"})
@Transactional
public class AsociacionDaoTest extends BaseTest {

    private static final Logger log = LoggerFactory.getLogger(AsociacionDaoTest.class);
    @Autowired
    private AsociacionDao instance;
    @Autowired
    private SessionFactory sessionFactory;

    private Session currentSession() {
        return sessionFactory.getCurrentSession();
    }

    /**
     * Test of lista method, of class AsociacionDao.
     */
    @Test
    public void debieraMostrarListaDeAsociaciones() {
        log.debug("Debiera mostrar lista de asociaciones");
        Union union = new Union("tst-01", Constantes.STATUS_ACTIVO);
        currentSession().save(union);
        for (int i = 0; i < 20; i++) {
            Asociacion asociacion = new Asociacion("tst-01", Constantes.STATUS_ACTIVO, union);
            currentSession().save(asociacion);
        }

        Map<String, Object> params = new HashMap<>();
        params.put("union", union.getId());
        Map result = instance.lista(params);
        assertNotNull(result.get("asociaciones"));
        assertNotNull(result.get("cantidad"));
        assertEquals(10, ((List<Asociacion>) result.get("asociaciones")).size());
        assertEquals(20, ((Long) result.get("cantidad")).intValue());
    }

    /**
     * Test of obtiene method, of class AsociacionDao.
     */
    @Test
    public void debieraObtenerAsociacion() {
        log.debug("Debiera obtener asociacion");
        Union union = new Union("test", Constantes.STATUS_ACTIVO);
        currentSession().save(union);
        Asociacion asociacion = new Asociacion("test-01", Constantes.STATUS_ACTIVO, union);
        currentSession().save(asociacion);
        Long id = asociacion.getId();
        assertNotNull(id);
        Asociacion result = instance.obtiene(id);
        assertEquals("test-01", result.getNombre());
    }

    /**
     * Test of crea method, of class AsociacionDao.
     */
    @Test
    public void debieraCrearAsociacion() {
        log.debug("Debiera crear asociacion");
        Union union = new Union("TEST01", Constantes.STATUS_ACTIVO);
        currentSession().save(union);
        Rol rol = new Rol("ROLE_TEST");
        currentSession().save(rol);
        Set<Rol> roles = new HashSet<>();
        roles.add(rol);
        Asociacion asociacion = new Asociacion("tst-01", Constantes.STATUS_ACTIVO, union);
        currentSession().save(asociacion);
        Usuario usuario = new Usuario("test-01@test.com", "test-01", "TEST1", "TEST");
        usuario.setAsociacion(asociacion);
        usuario.setRoles(roles);
        currentSession().save(usuario);
        Long id = usuario.getId();
        assertNotNull(id);

        asociacion = new Asociacion("test", Constantes.STATUS_ACTIVO, union);
        asociacion = instance.crea(asociacion, usuario);
        assertNotNull(asociacion);
        assertNotNull(asociacion.getId());
        assertEquals("test", asociacion.getNombre());
    }

    /**
     * Test of actualiza method, of class AsociacionDao.
     */
    @Test
    public void debieraActualizarAsociacion() {
        log.debug("Debiera actualizar asociacion");
        Union union = new Union("TEST01", Constantes.STATUS_ACTIVO);
        currentSession().save(union);
        assertNotNull(union);
        Rol rol = new Rol("ROLE_TEST");
        currentSession().save(rol);
        assertNotNull(rol);
        Set<Rol> roles = new HashSet<>();
        roles.add(rol);
        Asociacion asociacion = new Asociacion("tst-01", Constantes.STATUS_ACTIVO, union);
        currentSession().save(asociacion);
        assertNotNull(asociacion);
        Usuario usuario = new Usuario("test-01@test.com", "test-01", "TEST1", "TEST");
        usuario.setAsociacion(asociacion);
        usuario.setRoles(roles);
        currentSession().save(usuario);
        Long id = usuario.getId();
        assertNotNull(id);

        authenticate(usuario, usuario.getPassword(), new ArrayList<GrantedAuthority>(usuario.getRoles()));

        log.debug("Obteniendo asociacion");
        Asociacion result = instance.obtiene(asociacion.getId());
        assertNotNull(result);
        log.debug("Modificando nombre");
        result.setNombre("PRUEBA");
        log.debug("Enviando a actualizar asociacion");
        instance.actualiza(result, usuario);

        log.debug("Obteniendo asociacion nuevamente");
        Asociacion prueba = instance.obtiene(asociacion.getId());
        log.debug("Haciendo asserts");
        assertNotNull(prueba);
        assertEquals("PRUEBA", prueba.getNombre());
    }

    /**
     * Test of elimina method, of class AsociacionDao.
     */
    @Test(expected = UltimoException.class)
    public void noDebieraEliminarAsociacion() throws Exception {
        log.debug("Debiera actualizar asociacion");
        Union union = new Union("TEST01", Constantes.STATUS_ACTIVO);
        currentSession().save(union);
        Rol rol = new Rol("ROLE_TEST");
        currentSession().save(rol);
        Set<Rol> roles = new HashSet<>();
        roles.add(rol);
        Asociacion asociacion = new Asociacion("tst-01", Constantes.STATUS_ACTIVO, union);
        currentSession().save(asociacion);
        Usuario usuario = new Usuario("test-01@test.com", "test-01", "TEST1", "TEST");
        usuario.setAsociacion(asociacion);
        usuario.setRoles(roles);
        currentSession().save(usuario);
        Long id = usuario.getId();
        assertNotNull(id);
        assertNotNull(id);

        String nombre = instance.elimina(id, asociacion.getId());
        assertNotNull(nombre);
        assertEquals("TEST01", nombre);

        Asociacion prueba2 = instance.obtiene(id);
        assertNull(prueba2);
    }

    @Test
    public void debieraEliminarAsociacion() throws UltimoException {
        log.debug("Debiera actualizar asociacion");

        Union union = new Union("TEST01", Constantes.STATUS_ACTIVO);
        currentSession().save(union);
        Rol rol = new Rol("ROLE_TEST");
        currentSession().save(rol);
        Set<Rol> roles = new HashSet<>();
        roles.add(rol);
        Asociacion asociacion = new Asociacion("tst-01", Constantes.STATUS_ACTIVO, union);
        currentSession().save(asociacion);
        Usuario usuario = new Usuario("test-01@test.com", "test-01", "TEST1", "TEST");
        usuario.setAsociacion(asociacion);
        usuario.setRoles(roles);
        currentSession().save(usuario);
        Long id = usuario.getId();
        assertNotNull(id);
        id = asociacion.getId();
        currentSession().refresh(asociacion);
        currentSession().refresh(union);

        asociacion = new Asociacion("tst-02", Constantes.STATUS_ACTIVO, union);
        currentSession().save(asociacion);

        authenticate(usuario, usuario.getPassword(), new ArrayList<GrantedAuthority>(usuario.getRoles()));

        String nombre = instance.elimina(id, union.getId());
        assertNotNull(nombre);
        assertEquals("tst-01", nombre);

        asociacion = instance.obtiene(id);
        assertNull(asociacion);
    }
}