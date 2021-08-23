import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IVenta } from 'app/shared/model/venta.model';
import { getEntities as getVentas } from 'app/entities/venta/venta.reducer';
import { IProducto } from 'app/shared/model/producto.model';
import { getEntities as getProductos } from 'app/entities/producto/producto.reducer';
import { getEntity, updateEntity, createEntity, reset } from './venta-detalle.reducer';
import { IVentaDetalle } from 'app/shared/model/venta-detalle.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const VentaDetalleUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const ventas = useAppSelector(state => state.venta.entities);
  const productos = useAppSelector(state => state.producto.entities);
  const ventaDetalleEntity = useAppSelector(state => state.ventaDetalle.entity);
  const loading = useAppSelector(state => state.ventaDetalle.loading);
  const updating = useAppSelector(state => state.ventaDetalle.updating);
  const updateSuccess = useAppSelector(state => state.ventaDetalle.updateSuccess);

  const handleClose = () => {
    props.history.push('/venta-detalle' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getVentas({}));
    dispatch(getProductos({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...ventaDetalleEntity,
      ...values,
      venta: ventas.find(it => it.id.toString() === values.ventaId.toString()),
      producto: productos.find(it => it.id.toString() === values.productoId.toString()),
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
          ...ventaDetalleEntity,
          ventaId: ventaDetalleEntity?.venta?.id,
          productoId: ventaDetalleEntity?.producto?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="reactDemoApp.ventaDetalle.home.createOrEditLabel" data-cy="VentaDetalleCreateUpdateHeading">
            <Translate contentKey="reactDemoApp.ventaDetalle.home.createOrEditLabel">Create or edit a VentaDetalle</Translate>
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
                  id="venta-detalle-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('reactDemoApp.ventaDetalle.talla')}
                id="venta-detalle-talla"
                name="talla"
                data-cy="talla"
                type="text"
              />
              <ValidatedField
                label={translate('reactDemoApp.ventaDetalle.color')}
                id="venta-detalle-color"
                name="color"
                data-cy="color"
                type="text"
              />
              <ValidatedField
                label={translate('reactDemoApp.ventaDetalle.urlImagen')}
                id="venta-detalle-urlImagen"
                name="urlImagen"
                data-cy="urlImagen"
                type="text"
              />
              <ValidatedField
                label={translate('reactDemoApp.ventaDetalle.precio')}
                id="venta-detalle-precio"
                name="precio"
                data-cy="precio"
                type="text"
              />
              <ValidatedField
                label={translate('reactDemoApp.ventaDetalle.cantidad')}
                id="venta-detalle-cantidad"
                name="cantidad"
                data-cy="cantidad"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('reactDemoApp.ventaDetalle.subTotal')}
                id="venta-detalle-subTotal"
                name="subTotal"
                data-cy="subTotal"
                type="text"
              />
              <ValidatedField
                id="venta-detalle-venta"
                name="ventaId"
                data-cy="venta"
                label={translate('reactDemoApp.ventaDetalle.venta')}
                type="select"
              >
                <option value="" key="0" />
                {ventas
                  ? ventas.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="venta-detalle-producto"
                name="productoId"
                data-cy="producto"
                label={translate('reactDemoApp.ventaDetalle.producto')}
                type="select"
              >
                <option value="" key="0" />
                {productos
                  ? productos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/venta-detalle" replace color="info">
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

export default VentaDetalleUpdate;
