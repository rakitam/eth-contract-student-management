import {PredispitnaObaveza} from "../predispitne-obaveze/predispitna-obaveza.model";
import {Student} from "../studenti/student.model";

export interface PolaganjePredispitneObaveze {
  id?: number;
  datum: Date | string;
  bodovi: number;
  polozio: boolean;
  predispitnaObaveza: PredispitnaObaveza | null;
  student: Student | null;

}
