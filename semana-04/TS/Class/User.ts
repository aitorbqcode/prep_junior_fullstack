class User{
    private _id: number;
    // El ! se pone para indicar que la variable se le dara valor con el setter para inicializarla
    private _email!: string;
    private _name!: string;
    private _password!: string;
    private tasks: Task[] = [];

    constructor(
        id: number = Math.random(),
        email: string,
        name: string,
        password: string
    ){
        this._id = id;
        this.email = email;
        this.name = name;
        this.password = password;
    }

    //Setters
    set email(email: string) {
        if(email.trim().length == 0) throw new Error("Email invalido");
        this._email = email;
    }

    set name(name: string) {
        if(name.trim().length == 0) throw new Error("Nombre invalido");
        this._name = name;
    }

    set password(password: string) {
        if(password.trim().length < 6) throw new Error("Contraseña invalida");
        this._password = password;
    }

    //Getters
    get id(): number {
        return this._id;
    }

    get email(): string {
        return this._email;
    }

    get name(): string {
        return this._name;
    }   

    get password(): string {
        return this._password;
    }   

    get userTasks(): Task[] {
        return this.tasks;
    }


    //Methods

    static fromPlainObject(rawUser: any): User {
    return new User(
        rawUser.email,
        rawUser.name,
        rawUser.password,
        rawUser.id
    );
}
}