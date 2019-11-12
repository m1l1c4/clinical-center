/*!

=========================================================
* Paper Kit React - v1.0.0
=========================================================

* Product Page: https://www.creative-tim.com/product/paper-kit-react

* Copyright 2019 Creative Tim (https://www.creative-tim.com)
* Licensed under MIT (https://github.com/creativetimofficial/paper-kit-react/blob/master/LICENSE.md)

* Coded by Creative Tim

=========================================================

* The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

*/
import React from "react";

// reactstrap components
import { Button, Card, Form, FormGroup, Input, Container, Row, Col, Modal } from "reactstrap";

// core components
import ExamplesNavbar from "components/Navbars/ExamplesNavbar.js";
import RegisterModal from "components/RegisterModal";

function RegisterPage() {
  const [registerShow, setRegisterShow] = React.useState(false);
  document.documentElement.classList.remove("nav-open");
  React.useEffect(() => {
    document.body.classList.add("register-page");
    return function cleanup() {
      document.body.classList.remove("register-page");
    };
  });

  
  return (
    <>
      
      <ExamplesNavbar showRegister={() => setRegisterShow(true)}/>
      <div
        className="page-header"
        style={{
          backgroundImage: "url(" + require("assets/img/hc-complete-resize.jpg") + ")"
        }}
      >        
      </div>      
      <Modal modalClassName="modal-register" isOpen={registerShow}>
        <div className="modal-header no-border-header text-center">
            <h3 className="title mx-auto">Dobrodošli!</h3>
        </div>
        <div className="modal-body">                       
                
                  <FormGroup>
                  <label>JBO</label>
                  <Input placeholder="jedinstveni broj osiguranika" type="text" />
                  </FormGroup>
                  <FormGroup>
                  <label>Email</label>
                  <Input placeholder="email" type="text" />
                  </FormGroup>
                  <FormGroup>
                  <label>Ime</label>
                  <Input placeholder="ime" type="text" />
                  </FormGroup>
                  <FormGroup>
                  <label>Prezime</label>
                  <Input placeholder="prezime" type="text" />
                  </FormGroup>
                  <FormGroup>
                  <label>Lozinka</label>
                  <Input placeholder="lozinka" type="password" />
                  </FormGroup>
                  <FormGroup>
                  <label>Potvrdi lozinku</label>
                  <Input placeholder="lozinka" type="password" />
                  </FormGroup>
                  <FormGroup>
                  <label>Telefon</label>
                  <Input placeholder="telefon" type="text" />
                  </FormGroup>
                  <FormGroup>
                  <label>Adresa</label>
                  <Input placeholder="adresa" type="text" />
                  </FormGroup>
                  <FormGroup>
                  <label>Grad</label>
                  <Input placeholder="grad" type="text" />
                  </FormGroup>
                  <FormGroup>
                  <label>Država</label>
                  <Input placeholder="država" type="text" />
                  </FormGroup>                  
                  <Button block className="btn-round" color="info">
                    Registruj se
                  </Button>
               
                
        </div>
        </Modal>
        
        
    </>
  );
}

export default RegisterPage;
