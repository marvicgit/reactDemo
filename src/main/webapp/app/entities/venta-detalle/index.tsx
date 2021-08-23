import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import VentaDetalle from './venta-detalle';
import VentaDetalleDetail from './venta-detalle-detail';
import VentaDetalleUpdate from './venta-detalle-update';
import VentaDetalleDeleteDialog from './venta-detalle-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={VentaDetalleUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={VentaDetalleUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={VentaDetalleDetail} />
      <ErrorBoundaryRoute path={match.url} component={VentaDetalle} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={VentaDetalleDeleteDialog} />
  </>
);

export default Routes;
