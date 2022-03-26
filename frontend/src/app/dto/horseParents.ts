import {Horse} from "./horse";
import {Owner} from "./owner";

/**
 * Class for Horse DTOs.
 * Contains all common properties.
 * Owner and Parents are represented by DTOs.
 */
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
