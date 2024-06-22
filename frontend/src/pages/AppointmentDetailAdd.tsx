import React, {useState} from 'react';
import {useNavigate, useParams} from "react-router-dom";
import AppointmentService from "../services/AppointmentService.ts";
import CommonFormFields from "../layouts/CommonFormFields.tsx";

function AppointmentDetailAdd() {
    const [imageUrl, setImageUrl] = useState<string>('');
    const [description, setDescription] = useState<string>('');
    const navigate = useNavigate();
    const { id = '' } = useParams<string>();
    const appointmentService = new AppointmentService();
    const [errorMessage, setErrorMessage] = useState<string>('');

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        appointmentService.updateAppointmentScore(id, imageUrl, description)
            .then(response => {
                navigate(`/appointments/add-date/${response.data.id}`)
            })
            .catch(error => {
                if (error.response) {
                    setErrorMessage(error.response.data.message);
                }else {
                    setErrorMessage('Etwas ist schief gelaufen: ' + error.message);
                }
            });
    }

    return (
        <main className={'container'}>
            <CommonFormFields/>

            <div className="row g-5">
                <div className="col-md-12 col-lg-12">
                    <form onSubmit={handleSubmit}>
                        <div className="row g-3">

                            <div className="col-sm-6">
                                <label htmlFor="imageUrl" className="form-label">Bild-URL</label>
                                <input type="text" className="form-control" id="imageUrl"
                                       placeholder="Schreiben Sie Bild Url"
                                       onChange={(e) => setImageUrl(e.target.value)}/>
                            </div>

                            <div className="col-sm-6">
                                <label htmlFor="description" className="form-label">Beschreibung</label>
                                <input type="text" className="form-control" id="description"
                                       placeholder="Erklären Sie Ihre Krankheit"
                                       onChange={(e) => setDescription(e.target.value)}/>
                            </div>

                        </div>

                        <button className="w-100 btn btn-primary btn-lg my-4" type="submit">Weiter</button>
                    </form>

                    {errorMessage && (
                        <div className="alert alert-danger" role="alert">
                            {errorMessage}
                        </div>
                    )}

                </div>
            </div>
        </main>
    );
}

export default AppointmentDetailAdd;