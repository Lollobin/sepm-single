import {Horse} from "./horse";
import {Owner} from "./owner";

export interface HorseParents {
  id?: bigint;
  name: string;
  description?: string;
  dateOfBirth: Date;
  sex: Sex;
  owner?: Owner;
  father?: Horse;
  mother?: Horse;
}

type Sex = "male" | "female";
