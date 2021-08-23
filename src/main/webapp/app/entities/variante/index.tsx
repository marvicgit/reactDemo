import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Variante from './variante';
import VarianteDetail from './variante-detail';
import VarianteUpdate from './variante-update';
import VarianteDeleteDialog from './variante-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={VarianteUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={VarianteUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={VarianteDetail} />
      <ErrorBoundaryRoute path={match.url} component={Variante} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={VarianteDeleteDialog} />
  </>
);

export default Routes;
