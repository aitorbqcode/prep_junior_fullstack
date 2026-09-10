class LoginResponse {
    private _token!: string;
    private _user!: User;

    constructor(token: string, user: User) {
        this.token = token;
        this.user = user;
    }   

    //Setters
    set token(token: string) {
        if(token.trim().length == 0) throw new Error("Token invalido");
        this._token = token;
    }

    set user(user: User) {
        this._user = user;
    }

    //Getters
    get token(): string {
        return this._token;
    }

    get user(): User {
        return this._user;
    }
}