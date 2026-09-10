class Task {
    //Declaramos las variables con (_) para que no colisione y podamos usar los setters y getters
    private _id: number;
    // El ! se pone para indicar que la variable se le dara valor con el setter para inicializarla
    private _title!: string;
    private _completed!: boolean;
    private _createdAt!: Date;
    private _user!: User;
    
    //Constructor recibimos los valores y los asignamos a las variables privadas
    constructor(
        title: string,
        completed: boolean,
        createdAt: Date,
        user: User,
        id: number = Math.random()
    ){
        this.title = title;
        this.completed = completed;
        this.createdAt = createdAt;
        this.user = user;
        this._id = id;
    }

    //Setters

    set title(title: string) {
        if(title.trim().length == 0) throw new Error("Titulo invalido");
        this._title = title;
    }

    set completed(completed: boolean) {
        this._completed = completed;
    }

    set createdAt(createdAt: Date) {
        this._createdAt = createdAt;
    }

    set user(user: User) {
        this._user = user;
    }  
    
    //Getters

    get id(): number {
        return this._id;
    }       

    get title(): string {
        return this._title;
    }

    get completed(): boolean {
        return this._completed;
    }   

    get createdAt(): Date {
        return this._createdAt;
    }   

    get user(): User {
        return this._user;
    }
    
    get isOverdue(): boolean {
        const now = new Date();
        var sevenDays = new Date(this._createdAt);
        sevenDays.setDate(sevenDays.getDate() + 7);
        return now > sevenDays;
    }

    //Methods

    // 'response' es el objeto HTTP con data, status y message
    static fromApiResponse(response: ApiResponse<any>): Task {
        const rawTask = response.data; // Aquí está el JSON de la task
        return new Task(
            rawTask.title,
            rawTask.completed,
            new Date(rawTask.createdAt),
            User.fromPlainObject(rawTask.user),
            rawTask.id
    );
}

}