import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import SubCategoria from './sub-categoria';
import SubCategoriaDetail from './sub-categoria-detail';
import SubCategoriaUpdate from './sub-categoria-update';
import SubCategoriaDeleteDialog from './sub-categoria-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SubCategoriaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SubCategoriaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SubCategoriaDetail} />
      <ErrorBoundaryRoute path={match.url} component={SubCategoria} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={SubCategoriaDeleteDialog} />
  </>
);

export default Routes;
