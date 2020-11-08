# TaccolationMobileApp

## Brief overview
The Taccolations app is a mobile-friendly app designed specifically for the Android platform that offers
*teachers* the platform to manage students records in an efficient manner. It increases Teacher’s
productivity as usual manual work such as attendance, lecture notes, assessments, reports are now
digitized and automated. It offers a platform where student’s information can better be managed
thereby giving the teacher an idea on aspects to improve and also aspects to impact more in the
student’s life.

## Dependencies
1. Android Navigation Architecture with a single activity design pattern.
2. Hilt for dependency injection.
3. Google Cloud Firestore and Storage
4. Room Database
5. Data Binding framework.
6. Android Material Design.

## App Architecture and design
1. CLEAN Architecture with a mix of MVVM for the app's Presentation Layer.
2. SOLID Principles.

### Packages
1. *App:* contains all UI data including dependency injection, utilities etc
2. *Presentation:* contains ViewModels
3. *Domain:* contains the App's usecases and entities. Defines contract for the other layers
4. *Data:* contains references and defines the contract for the data sources (Remote and Local)
5. *Remote:* contains retrofit API service, and repository implementations
6. *Local:* no implementations. Just for backup if the need arise to use local persistence like ROOM DB

## Screenshots

![onboard](https://user-images.githubusercontent.com/65837990/97706649-4e99bb80-1ab6-11eb-90f1-186cf81a11db.png)
![login](https://user-images.githubusercontent.com/65837990/97706639-4b9ecb00-1ab6-11eb-828d-b6ca8fd5edab.png)
![login_progress](https://user-images.githubusercontent.com/65837990/97706643-4c376180-1ab6-11eb-99d2-dba61b624367.png)
![register1](https://user-images.githubusercontent.com/65837990/97706654-4fcae880-1ab6-11eb-872c-937a22ce358d.png)
![register2](https://user-images.githubusercontent.com/65837990/97706655-50fc1580-1ab6-11eb-9a53-010073555d55.png)
![resendlink](https://user-images.githubusercontent.com/65837990/97706659-5194ac00-1ab6-11eb-945e-ac2e4036ff85.png)
![loading](https://user-images.githubusercontent.com/65837990/97706629-46da1700-1ab6-11eb-82ee-69f1f2b42ec1.png)
![dashboard](https://user-images.githubusercontent.com/65837990/98487423-18310e80-2223-11eb-92ce-7f5edb883279.png)
![dashboard2](https://user-images.githubusercontent.com/65837990/98487418-15ceb480-2223-11eb-8520-48a4ead2d1f9.png)
![navigation](https://user-images.githubusercontent.com/65837990/98487424-18c9a500-2223-11eb-8cbb-b1a1e8a418bc.png)
![addStudent](https://user-images.githubusercontent.com/65837990/98487425-18c9a500-2223-11eb-8ff0-ca2ec42e2e37.png)
![notes](https://user-images.githubusercontent.com/65837990/98487427-19fad200-2223-11eb-8462-42ccd5ce13d8.png)
![teacher_profile](https://user-images.githubusercontent.com/65837990/98487429-19fad200-2223-11eb-8d88-2a6a39eb1a2c.png)
![edit_teacher_profile](https://user-images.githubusercontent.com/65837990/98487431-1a936880-2223-11eb-9fa2-fd22d4ddffcb.png)
![tasks](https://user-images.githubusercontent.com/65837990/98487432-1b2bff00-2223-11eb-8c34-4d40df501fa6.png)
![student_profile](https://user-images.githubusercontent.com/65837990/98487433-1bc49580-2223-11eb-90e8-59d727df0108.png)
![student_profile](https://user-images.githubusercontent.com/65837990/97706666-52c5d900-1ab6-11eb-862a-8e9074e767d1.png)
![leaderboard](https://user-images.githubusercontent.com/65837990/98487434-1bc49580-2223-11eb-97e4-6d67be5ba838.png)
![settings](https://user-images.githubusercontent.com/65837990/98487426-19623b80-2223-11eb-9484-1f2b131d2151.png)
![notifications](https://user-images.githubusercontent.com/65837990/98487421-17987800-2223-11eb-92e1-44fc134b1ff6.png)
![report_sheet](https://user-images.githubusercontent.com/65837990/98487552-e10f2d00-2223-11eb-94b5-603783cf5a8b.png)

