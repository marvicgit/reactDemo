import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CompraDetalle from './compra-detalle';
import CompraDetalleDetail from './compra-detalle-detail';
import CompraDetalleUpdate from './compra-detalle-update';
import CompraDetalleDeleteDialog from './compra-detalle-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CompraDetalleUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CompraDetalleUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CompraDetalleDetail} />
      <ErrorBoundaryRoute path={match.url} component={CompraDetalle} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CompraDetalleDeleteDialog} />
  </>
);

export default Routes;
