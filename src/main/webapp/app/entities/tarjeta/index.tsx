import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Tarjeta from './tarjeta';
import TarjetaDetail from './tarjeta-detail';
import TarjetaUpdate from './tarjeta-update';
import TarjetaDeleteDialog from './tarjeta-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TarjetaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TarjetaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TarjetaDetail} />
      <ErrorBoundaryRoute path={match.url} component={Tarjeta} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TarjetaDeleteDialog} />
  </>
);

export default Routes;
