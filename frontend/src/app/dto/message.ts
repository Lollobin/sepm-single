/**
 * Class for message DTOs.
 * Only used in frontend.
 */
export interface Message {
  text: string;
  level: Level;
}

type Level = "success" | "info" | "error" | "warning";
