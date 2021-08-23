import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { Translate, translate } from 'react-jhipster';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.main')}
    id="entity-menu"
    data-cy="entity"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <>{/* to avoid warnings when empty */}</>
    <MenuItem icon="asterisk" to="/categoria">
      <Translate contentKey="global.menu.entities.categoria" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/sub-categoria">
      <Translate contentKey="global.menu.entities.subCategoria" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/producto">
      <Translate contentKey="global.menu.entities.producto" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/variante">
      <Translate contentKey="global.menu.entities.variante" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/imagen">
      <Translate contentKey="global.menu.entities.imagen" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/cliente">
      <Translate contentKey="global.menu.entities.cliente" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/venta">
      <Translate contentKey="global.menu.entities.venta" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/venta-detalle">
      <Translate contentKey="global.menu.entities.ventaDetalle" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/tarjeta">
      <Translate contentKey="global.menu.entities.tarjeta" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/compra">
      <Translate contentKey="global.menu.entities.compra" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/compra-detalle">
      <Translate contentKey="global.menu.entities.compraDetalle" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
