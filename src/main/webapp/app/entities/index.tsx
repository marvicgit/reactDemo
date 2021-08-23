import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Categoria from './categoria';
import SubCategoria from './sub-categoria';
import Producto from './producto';
import Variante from './variante';
import Imagen from './imagen';
import Cliente from './cliente';
import Venta from './venta';
import VentaDetalle from './venta-detalle';
import Tarjeta from './tarjeta';
import Compra from './compra';
import CompraDetalle from './compra-detalle';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}categoria`} component={Categoria} />
      <ErrorBoundaryRoute path={`${match.url}sub-categoria`} component={SubCategoria} />
      <ErrorBoundaryRoute path={`${match.url}producto`} component={Producto} />
      <ErrorBoundaryRoute path={`${match.url}variante`} component={Variante} />
      <ErrorBoundaryRoute path={`${match.url}imagen`} component={Imagen} />
      <ErrorBoundaryRoute path={`${match.url}cliente`} component={Cliente} />
      <ErrorBoundaryRoute path={`${match.url}venta`} component={Venta} />
      <ErrorBoundaryRoute path={`${match.url}venta-detalle`} component={VentaDetalle} />
      <ErrorBoundaryRoute path={`${match.url}tarjeta`} component={Tarjeta} />
      <ErrorBoundaryRoute path={`${match.url}compra`} component={Compra} />
      <ErrorBoundaryRoute path={`${match.url}compra-detalle`} component={CompraDetalle} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
