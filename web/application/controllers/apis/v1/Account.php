<?php
    defined('BASEPATH') OR exit('No direct script access allowed');
    class Account extends CI_Controller {
        /**
        * Index Page for this controller.
        *
        * Maps to the following URL
        * 		http://example.com/index.php/welcome
        *	- or -
        * 		http://example.com/index.php/welcome/index
        *	- or -
        * Since this controller is set as the default controller in
        * config/routes.php, it's displayed at http://example.com/
        *
        * So any other public methods not prefixed with an underscore will
        * map to /index.php/welcome/<method_name>
        * @see https://codeigniter.com/user_guide/general/urls.html
        */
        public function __construct(){
            parent::__construct();
            $this->load->model(array('api_model', 'user_model'));
            $this->load->helper(array('json_output_helper'));
            $this->load->library('form_validation');
        }

        public function auth(){
            $method = $_SERVER['REQUEST_METHOD'];
            if ($method != 'POST'){
                return json_output(405, array('status'=>405, 'message'=>'Bad Request'));
            }else{
                if($this->api_model->validate_auth()){
    
                    $form_data = json_decode(file_get_contents('php://input'), true);
                    $this->form_validation->set_error_delimiters('', '');
                    if ($form_data){
                        $this->form_validation->set_data($form_data);
                    }else{
                // $form_data = $this->input->post();
            }
    
    
                    $this->form_validation->set_rules('fullname', 'Full Name', 'trim|required|min_length[4]|max_length[50]');
                    $this->form_validation->set_rules('email', 'Email', 'trim|required|valid_email|min_length[4]');
                    $this->form_validation->set_rules('phone', 'Phone', 'trim|required|min_length[10]|min_length[4]');
    
                    if ($this->form_validation->run() === false){
                        return json_output(401, array('status'=>401, 'message'=>'Authentication Failed', 'errors'=> validation_errors()));
                    }else{
    
                        $user_data = array(
                            'fullname'=>$form_data['fullname'],
                            'email'=>$form_data['email'],
                            'phone'=>$form_data['phone'],                            
                        );

                        
                        $user_data['session_cookie'] = random_string('alnum', 10);
    
                        $user = $this->user_model->authenticate_user($user_data);
                        // if($user_id){
                            // $user = $this->user_model->get_user($user_id);
                            if(!$user){
                                return json_output(503, array('status'=>503, 'message'=>'Server Unresponsive'));
                            }
    
                            return json_output(200, array(
                                'status' => 200,
                                'message' => 'Authentication Successful',
                                'session_cookie' => $user->session_cookie,
                                'user'=>$user
                            ));
    
                        // }
                        return json_output(503, array('status'=>503, 'message'=>'Unresponsive Server'));
    
                    }
                }else{
                    return json_output(403, array('status'=>403, 'message'=>'Forbidden'));
                }
            }
        }


        public function create_plan(){
            $method = $_SERVER['REQUEST_METHOD'];
            if ($method != 'POST'){
                return json_output(405, array('status'=>405, 'message'=>'Bad Request'));
            }else{
                if(!$this->api_model->validate_auth()){
                    return json_output(403, array('status'=>403, 'message'=>'Forbidden'));
                }
    
                if(!$this->user_model->validate_user()){
                    return json_output(403, array('status'=>403, 'message'=>'User Authentication Failed'));
                }

                $post_data = json_decode(file_get_contents('php://input'), true);
                $this->form_validation->set_error_delimiters('', '');
                if ($post_data){
                    $this->form_validation->set_data($post_data);
                }else{
                    $post_data = $this->input->post();
                }
        
                $this->form_validation->set_rules('starting_amount', 'Starting Amount', 'trim|required|numeric');
                $this->form_validation->set_rules('plan_type', 'Plan Type', 'trim|callback_plan_type_check');
                
                if ($this->form_validation->run() === false){
                    return json_output(401, array('message'=>'Incomplete Fields', 'errors'=> validation_errors()));
                }
                $user_id = $this->input->get_request_header('User-Id', TRUE);
                if(!$user_id){
                    return json_output(503, array('message'=>'Server Unresponsive'));
                }
                $form_data = array(
                    'starting_amount' => $post_data['starting_amount'],
                    'plan_type' => $post_data['plan_type'],
                    'balance' => $post_data['starting_amount'],
                    'user_id' => $user_id
                );
                
                $form_data['records'] = json_encode($form_data);

                $plan = $this->api_model->create_plan($form_data);
                return json_output(200, array(
                    'message' => 'Success',
                    'plan' => $plan
                ));
                
                return json_output(503, array('message'=>'Unresponsive Server'));
            }
        }
        
        public function modify_plan(){
            $method = $_SERVER['REQUEST_METHOD'];
            if ($method != 'POST'){
                return json_output(405, array('message'=>'Bad Request'));
            }
            if(!$this->api_model->validate_auth()){
                return json_output(403, array('message'=>'Forbidden'));
            }

            if(!$this->user_model->validate_user()){
                return json_output(403, array( 'message'=>'User Authentication Failed'));
            }
        }


        public function get_user_plan(){
            $method = $_SERVER['REQUEST_METHOD'];
            if ($method != 'GET'){
                return json_output(405, array('message'=>'Bad Request'));
            }
            if(!$this->api_model->validate_auth()){
                return json_output(403, array('message'=>'Forbidden'));
            }

            if(!$this->user_model->validate_user()){
                return json_output(403, array('message'=>'User Authentication Failed'));
            }

            return json_output(200, array('message'=>'Success', 'account' => $this->api_model->get_user_plan() ));
        }
        
        public function plan_type_check($input){
            $payment_types = array('credit', 'debit');
            if (in_array($input, $payment_types)){
                return true;
            }else{
                $this->form_validation->set_message('plan_type_check', 'The {field} input is not a recognizable type');
                return false;
            }
        }

    }