import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Imagen from './imagen';
import ImagenDetail from './imagen-detail';
import ImagenUpdate from './imagen-update';
import ImagenDeleteDialog from './imagen-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ImagenUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ImagenUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ImagenDetail} />
      <ErrorBoundaryRoute path={match.url} component={Imagen} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ImagenDeleteDialog} />
  </>
);

export default Routes;
