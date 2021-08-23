import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IProducto } from 'app/shared/model/producto.model';
import { getEntities as getProductos } from 'app/entities/producto/producto.reducer';
import { IImagen } from 'app/shared/model/imagen.model';
import { getEntities as getImagens } from 'app/entities/imagen/imagen.reducer';
import { getEntity, updateEntity, createEntity, reset } from './variante.reducer';
import { IVariante } from 'app/shared/model/variante.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const VarianteUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const productos = useAppSelector(state => state.producto.entities);
  const imagens = useAppSelector(state => state.imagen.entities);
  const varianteEntity = useAppSelector(state => state.variante.entity);
  const loading = useAppSelector(state => state.variante.loading);
  const updating = useAppSelector(state => state.variante.updating);
  const updateSuccess = useAppSelector(state => state.variante.updateSuccess);

  const handleClose = () => {
    props.history.push('/variante' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getProductos({}));
    dispatch(getImagens({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...varianteEntity,
      ...values,
      producto: productos.find(it => it.id.toString() === values.productoId.toString()),
      imagen: imagens.find(it => it.id.toString() === values.imagenId.toString()),
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
          ...varianteEntity,
          productoId: varianteEntity?.producto?.id,
          imagenId: varianteEntity?.imagen?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="reactDemoApp.variante.home.createOrEditLabel" data-cy="VarianteCreateUpdateHeading">
            <Translate contentKey="reactDemoApp.variante.home.createOrEditLabel">Create or edit a Variante</Translate>
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
                  id="variante-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('reactDemoApp.variante.sku')} id="variante-sku" name="sku" data-cy="sku" type="text" />
              <ValidatedField
                label={translate('reactDemoApp.variante.talla')}
                id="variante-talla"
                name="talla"
                data-cy="talla"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('reactDemoApp.variante.color')}
                id="variante-color"
                name="color"
                data-cy="color"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('reactDemoApp.variante.stock')}
                id="variante-stock"
                name="stock"
                data-cy="stock"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('reactDemoApp.variante.precio')}
                id="variante-precio"
                name="precio"
                data-cy="precio"
                type="text"
              />
              <ValidatedField
                id="variante-producto"
                name="productoId"
                data-cy="producto"
                label={translate('reactDemoApp.variante.producto')}
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
              <ValidatedField
                id="variante-imagen"
                name="imagenId"
                data-cy="imagen"
                label={translate('reactDemoApp.variante.imagen')}
                type="select"
              >
                <option value="" key="0" />
                {imagens
                  ? imagens.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/variante" replace color="info">
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

export default VarianteUpdate;
