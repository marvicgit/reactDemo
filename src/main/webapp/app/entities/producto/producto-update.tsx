import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { ISubCategoria } from 'app/shared/model/sub-categoria.model';
import { getEntities as getSubCategorias } from 'app/entities/sub-categoria/sub-categoria.reducer';
import { getEntity, updateEntity, createEntity, reset } from './producto.reducer';
import { IProducto } from 'app/shared/model/producto.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ProductoUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const subCategorias = useAppSelector(state => state.subCategoria.entities);
  const productoEntity = useAppSelector(state => state.producto.entity);
  const loading = useAppSelector(state => state.producto.loading);
  const updating = useAppSelector(state => state.producto.updating);
  const updateSuccess = useAppSelector(state => state.producto.updateSuccess);

  const handleClose = () => {
    props.history.push('/producto' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getSubCategorias({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...productoEntity,
      ...values,
      subCategoria: subCategorias.find(it => it.id.toString() === values.subCategoriaId.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...productoEntity,
          subCategoriaId: productoEntity?.subCategoria?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="reactDemoApp.producto.home.createOrEditLabel" data-cy="ProductoCreateUpdateHeading">
            <Translate contentKey="reactDemoApp.producto.home.createOrEditLabel">Create or edit a Producto</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="producto-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('reactDemoApp.producto.nombre')}
                id="producto-nombre"
                name="nombre"
                data-cy="nombre"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('reactDemoApp.producto.descripcion')}
                id="producto-descripcion"
                name="descripcion"
                data-cy="descripcion"
                type="text"
              />
              <ValidatedField
                label={translate('reactDemoApp.producto.precio')}
                id="producto-precio"
                name="precio"
                data-cy="precio"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('reactDemoApp.producto.stock')}
                id="producto-stock"
                name="stock"
                data-cy="stock"
                type="text"
              />
              <ValidatedField
                label={translate('reactDemoApp.producto.tallas')}
                id="producto-tallas"
                name="tallas"
                data-cy="tallas"
                type="text"
              />
              <ValidatedField
                label={translate('reactDemoApp.producto.colores')}
                id="producto-colores"
                name="colores"
                data-cy="colores"
                type="text"
              />
              <ValidatedField label={translate('reactDemoApp.producto.tags')} id="producto-tags" name="tags" data-cy="tags" type="text" />
              <ValidatedField
                label={translate('reactDemoApp.producto.venta')}
                id="producto-venta"
                name="venta"
                data-cy="venta"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('reactDemoApp.producto.descuento')}
                id="producto-descuento"
                name="descuento"
                data-cy="descuento"
                type="text"
              />
              <ValidatedField
                label={translate('reactDemoApp.producto.nuevo')}
                id="producto-nuevo"
                name="nuevo"
                data-cy="nuevo"
                check
                type="checkbox"
              />
              <ValidatedField
                id="producto-subCategoria"
                name="subCategoriaId"
                data-cy="subCategoria"
                label={translate('reactDemoApp.producto.subCategoria')}
                type="select"
              >
                <option value="" key="0" />
                {subCategorias
                  ? subCategorias.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/producto" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default ProductoUpdate;
