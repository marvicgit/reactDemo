import { IProducto } from 'app/shared/model/producto.model';
import { ICategoria } from 'app/shared/model/categoria.model';

export interface ISubCategoria {
  id?: number;
  nombre?: string;
  descripcion?: string | null;
  productos?: IProducto[] | null;
  categoria?: ICategoria | null;
}

export const defaultValue: Readonly<ISubCategoria> = {};
