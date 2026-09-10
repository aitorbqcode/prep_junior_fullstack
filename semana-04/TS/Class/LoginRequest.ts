class LoginRequest {
    private _email!: string
    private _password!: string

    constructor(email: string, password: string) {
        this.email = email
        this.password = password
    }

    //Setters
    set email(email: string) {
        if(email.trim().length == 0) throw new Error("Email invalido");
        this._email = email;
    }
    
    set password(password: string) {
        if(password.trim().length < 6) throw new Error("Contraseña invalida");
        this._password = password;
    }

    //Getters
    get email(): string {
        return this._email;
    }   

    get password(): string {
        return this._password;
    }   
}