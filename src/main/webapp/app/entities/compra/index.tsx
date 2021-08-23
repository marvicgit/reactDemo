import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Compra from './compra';
import CompraDetail from './compra-detail';
import CompraUpdate from './compra-update';
import CompraDeleteDialog from './compra-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CompraUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CompraUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CompraDetail} />
      <ErrorBoundaryRoute path={match.url} component={Compra} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CompraDeleteDialog} />
  </>
);

export default Routes;
