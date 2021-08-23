import { IVariante } from 'app/shared/model/variante.model';
import { IProducto } from 'app/shared/model/producto.model';

export interface IImagen {
  id?: number;
  indice?: number;
  nombre?: string;
  peso?: number;
  extension?: string;
  imagenContentType?: string;
  imagen?: string;
  variantes?: IVariante[] | null;
  producto?: IProducto | null;
}

export const defaultValue: Readonly<IImagen> = {};
