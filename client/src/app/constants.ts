export class Constants{
    public static get PAUSE_URL(): string {return "http://localhost:8080/api/generation/pause"}
    public static get STOP_URL(): string {return "http://localhost:8080/api/generation/stop"}
    public static get PLAY_URL(): string {return "http://localhost:8080/api/generation/play"}
    public static get START_URL(): string { return "http://localhost:8080/api/generation/start"}
    public static get GET_STATE_URL(): string {return "http://localhost:8080/api/map/file"}

}