2026-10-21T13:55:02.8268542Z ##[warning]Unexpected input(s) 'api-level', 'build-tools', valid inputs are ['cmdline-tools-version', 'accept-android-sdk-licenses', 'log-accepted-android-sdk-licenses', 'packages']
2026-10-21T13:55:02.8274928Z ##[group]Run android-actions/setup-android@v3
2026-10-21T13:55:02.8275303Z with:
2026-10-21T13:55:02.8275513Z   api-level: 34
2026-10-21T13:55:02.8275759Z   build-tools: 34.0.0
2026-10-21T13:55:02.8276030Z   cmdline-tools-version: 12266719
2026-10-21T13:55:02.8276358Z   accept-android-sdk-licenses: true
2026-10-21T13:55:02.8276655Z   log-accepted-android-sdk-licenses: true
2026-10-21T13:55:02.8277061Z   packages: tools platform-tools
2026-10-21T13:55:02.8277293Z env:
2026-10-21T13:55:02.8277563Z   JAVA_HOME: /opt/hostedtoolcache/Java_Temurin-Hotspot_jdk/17.0.20-1/x64
2026-10-21T13:55:02.8278019Z   JAVA_HOME_17_X64: /opt/hostedtoolcache/Java_Temurin-Hotspot_jdk/17.0.20-1/x64
2026-10-21T13:55:02.8278374Z ##[endgroup]
2026-10-21T13:55:02.9341330Z Found preinstalled sdkmanager in /usr/local/lib/android/sdk/cmdline-tools/latest with following source.properties:
2026-10-21T13:55:02.9342078Z Pkg.Revision=12.0
2026-10-21T13:55:02.9342250Z Pkg.Path=cmdline-tools;12.0
2026-10-21T13:55:02.9342413Z Pkg.Desc=Android SDK Command-line Tools
2026-10-21T13:55:02.9342532Z 
2026-10-21T13:55:02.9342634Z Wrong version in preinstalled sdkmanager
2026-10-21T13:55:02.9343020Z Downloading commandline tools from https://dl.google.com/android/repository/commandlinetools-linux-12266719_latest.zip
2026-10-21T13:55:03.5965552Z [command]/usr/bin/unzip -o -q /home/runner/work/_temp/51a77522-b286-4888-9b53-281e1ee546f7
2026-10-21T13:55:04.1216504Z Accepting Android SDK licenses
2026-10-21T13:55:04.1221351Z [command]/usr/local/lib/android/sdk/cmdline-tools/16.0/bin/sdkmanager --licenses
2026-10-21T13:55:08.0035265Z Loading local repository...                                                     
2026-10-21T13:55:08.0036036Z [=========                              ] 25% Loading local repository...       
2026-10-21T13:55:09.2118203Z [=========                              ] 25% Fetch remote repository...        
2026-10-21T13:55:09.5315966Z [==========                             ] 26% Fetch remote repository...        
2026-10-21T13:55:09.5317512Z [============                           ] 31% Fetch remote repository...        
2026-10-21T13:55:09.5442389Z [============                           ] 32% Fetch remote repository...        
2026-10-21T13:55:09.5447229Z [=============                          ] 33% Fetch remote repository...        
2026-10-21T13:55:09.5647189Z [=============                          ] 35% Fetch remote repository...        
2026-10-21T13:55:09.5649740Z [==============                         ] 36% Fetch remote repository...        
2026-10-21T13:55:09.5756231Z [==============                         ] 37% Fetch remote repository...        
2026-10-21T13:55:09.5757238Z [===============                        ] 38% Fetch remote repository...        
2026-10-21T13:55:09.5857208Z [===============                        ] 39% Fetch remote repository...        
2026-10-21T13:55:09.5873988Z [================                       ] 40% Fetch remote repository...        
2026-10-21T13:55:09.6102152Z [================                       ] 41% Fetch remote repository...        
2026-10-21T13:55:09.6107980Z [================                       ] 42% Fetch remote repository...        
2026-10-21T13:55:09.6233490Z [=================                      ] 44% Fetch remote repository...        
2026-10-21T13:55:09.6234667Z [=================                      ] 45% Fetch remote repository...        
2026-10-21T13:55:09.6354000Z [==================                     ] 46% Fetch remote repository...        
2026-10-21T13:55:09.6356206Z [==================                     ] 47% Fetch remote repository...        
2026-10-21T13:55:09.6455169Z [===================                    ] 48% Fetch remote repository...        
2026-10-21T13:55:09.6478828Z [===================                    ] 49% Fetch remote repository...        
2026-10-21T13:55:09.6555636Z [====================                   ] 50% Fetch remote repository...        
2026-10-21T13:55:09.6556521Z [====================                   ] 51% Fetch remote repository...        
2026-10-21T13:55:09.6661173Z [====================                   ] 53% Fetch remote repository...        
2026-10-21T13:55:09.6663984Z [=====================                  ] 54% Fetch remote repository...        
2026-10-21T13:55:09.6766996Z [=====================                  ] 55% Fetch remote repository...        
2026-10-21T13:55:09.6767843Z [======================                 ] 56% Fetch remote repository...        
2026-10-21T13:55:09.6846482Z [======================                 ] 57% Fetch remote repository...        
2026-10-21T13:55:09.6850607Z [=======================                ] 58% Fetch remote repository...        
2026-10-21T13:55:09.6944261Z [=======================                ] 59% Fetch remote repository...        
2026-10-21T13:55:09.6945019Z [========================               ] 60% Fetch remote repository...        
2026-10-21T13:55:09.7047014Z [========================               ] 61% Fetch remote repository...        
2026-10-21T13:55:09.7047869Z [=========================              ] 63% Fetch remote repository...        
2026-10-21T13:55:09.7139967Z [=========================              ] 64% Fetch remote repository...        
2026-10-21T13:55:09.7140741Z [=========================              ] 65% Fetch remote repository...        
2026-10-21T13:55:09.7340041Z [==========================             ] 66% Fetch remote repository...        
2026-10-21T13:55:09.7363676Z [==========================             ] 67% Fetch remote repository...        
2026-10-21T13:55:09.7431030Z [===========================            ] 68% Fetch remote repository...        
2026-10-21T13:55:09.7461923Z [===========================            ] 69% Fetch remote repository...        
2026-10-21T13:55:09.7902504Z [============================           ] 70% Fetch remote repository...        
2026-10-21T13:55:09.7903349Z [============================           ] 72% Fetch remote repository...        
2026-10-21T13:55:09.8163158Z [=============================          ] 73% Fetch remote repository...        
2026-10-21T13:55:09.8164326Z [=============================          ] 74% Fetch remote repository...        
2026-10-21T13:55:09.8237273Z [=============================          ] 75% Fetch remote repository...        
2026-10-21T13:55:09.8310139Z [=============================          ] 75% Computing updates...              
2026-10-21T13:55:09.8315952Z [=======================================] 100% Computing updates...             
2026-10-21T13:55:09.8717881Z 7 of 8 SDK package licenses not accepted.
2026-10-21T13:55:09.8726660Z Review licenses that have not been accepted (y/N)? 
2026-10-21T13:55:09.8747563Z 1/7: License android-googletv-license:
2026-10-21T13:55:09.8749559Z ---------------------------------------
2026-10-21T13:55:09.8749910Z Terms and Conditions
2026-10-21T13:55:09.8750038Z 
2026-10-21T13:55:09.8750263Z This is the Google TV Add-on for the Android Software Development Kit License Agreement.
2026-10-21T13:55:09.8750592Z 
2026-10-21T13:55:09.8750668Z 1. Introduction
2026-10-21T13:55:09.8750780Z 
2026-10-21T13:55:09.8751934Z 1.1 The Google TV Add-on for the Android Software Development Kit (referred to in this License Agreement as the "Google TV Add-on" and specifically including the Android system files, packaged APIs, and Google APIs add-ons) is licensed to you subject to the terms of this License Agreement. This License Agreement forms a legally binding contract between you and Google in relation to your use of the Google TV Add-on.
2026-10-21T13:55:09.8753220Z 
2026-10-21T13:55:09.8753646Z 1.2 "Google" means Google Inc., a Delaware corporation with principal place of business at 1600 Amphitheatre Parkway, Mountain View, CA 94043, United States.
2026-10-21T13:55:09.8754153Z 
2026-10-21T13:55:09.8754245Z 2. Accepting this License Agreement
2026-10-21T13:55:09.8754728Z 
2026-10-21T13:55:09.8755170Z 2.1 In order to use the Google TV Add-on, you must first agree to this License Agreement. You may not use the Google TV Add-on if you do not accept this License Agreement.
2026-10-21T13:55:09.8755703Z 
2026-10-21T13:55:09.8755810Z 2.2 You can accept this License Agreement by:
2026-10-21T13:55:09.8756000Z 
2026-10-21T13:55:09.8756268Z (A) clicking to accept or agree to this License Agreement, where this option is made available to you; or
2026-10-21T13:55:09.8756617Z 
2026-10-21T13:55:09.8757206Z (B) by actually using the Google TV Add-on. In this case, you agree that use of the Google TV Add-on constitutes acceptance of the License Agreement from that point onwards.
2026-10-21T13:55:09.8757741Z 
2026-10-21T13:55:09.8758536Z 2.3 You may not use the Google TV Add-on and may not accept the Licensing Agreement if you are a person barred from receiving the Google TV Add-on under the laws of the United States or other countries including the country in which you are resident or from which you use the Google TV Add-on.
2026-10-21T13:55:09.8759434Z 
2026-10-21T13:55:09.8760667Z 2.4 If you are agreeing to be bound by this License Agreement on behalf of your employer or other entity, you represent and warrant that you have full legal authority to bind your employer or such entity to this License Agreement. If you do not have the requisite authority, you may not accept the Licensing Agreement or use the Google TV Add-on on behalf of your employer or other entity.
2026-10-21T13:55:09.8780014Z 
2026-10-21T13:55:09.8780362Z 3. Google TV Add-on License from Google
2026-10-21T13:55:09.8780694Z 
2026-10-21T13:55:09.8781504Z 3.1 Subject to the terms of this License Agreement, Google grants you a limited, worldwide, royalty-free, non- assignable and non-exclusive license to use the Google TV Add-on solely to develop applications to run on the Google TV platform.
2026-10-21T13:55:09.8793988Z 
2026-10-21T13:55:09.8795404Z 3.2 You agree that Google or third parties own all legal right, title and interest in and to the Google TV Add-on, including any Intellectual Property Rights that subsist in the Google TV Add-on. "Intellectual Property Rights" means any and all rights under patent law, copyright law, trade secret law, trademark law, and any and all other proprietary rights. Google reserves all rights not expressly granted to you.
2026-10-21T13:55:09.8804287Z 
2026-10-21T13:55:09.8810152Z 3.3 Except to the extent required by applicable third party licenses, you may not copy (except for backup purposes), modify, adapt, redistribute, decompile, reverse engineer, disassemble, or create derivative works of the Google TV Add-on or any part of the Google TV Add-on. Except to the extent required by applicable third party licenses, you may not load any part of the Google TV Add-on onto a mobile handset, television, or any other hardware device except a personal computer, combine any part of the Google TV Add-on with other software, or distribute any software or device incorporating a part of the Google TV Add-on.
2026-10-21T13:55:09.8837136Z 
2026-10-21T13:55:09.8849954Z 3.4 Use, reproduction and distribution of components of the Google TV Add-on licensed under an open source software license are governed solely by the terms of that open source software license and not this License Agreement.
2026-10-21T13:55:09.8850802Z 
2026-10-21T13:55:09.8868191Z 3.5 You agree that the form and nature of the Google TV Add-on that Google provides may change without prior notice to you and that future versions of the Google TV Add-on may be incompatible with applications developed on previous versions of the Google TV Add-on. You agree that Google may stop (permanently or temporarily) providing the Google TV Add-on (or any features within the Google TV Add-on) to you or to users generally at Google's sole discretion, without prior notice to you.
2026-10-21T13:55:09.8869731Z 
2026-10-21T13:55:09.8889411Z 3.6 Nothing in this License Agreement gives you a right to use any of Google's or it’s licensors’ trade names, trademarks, service marks, logos, domain names, or other distinctive brand features.
2026-10-21T13:55:09.8890440Z 
2026-10-21T13:55:09.8891195Z 3.7 You agree that you will not remove, obscure, or alter any proprietary rights notices (including copyright and trademark notices) that may be affixed to or contained within the Google TV Add-on.
2026-10-21T13:55:09.8891883Z 
2026-10-21T13:55:09.8892091Z 4. Use of the Google TV Add-on by You
2026-10-21T13:55:09.8892254Z 
2026-10-21T13:55:09.8893008Z 4.1 Google agrees that it obtains no right, title or interest from you (or your licensors) under this License Agreement in or to any software applications that you develop using the Google TV Add-on, including any intellectual property rights that subsist in those applications.
2026-10-21T13:55:09.8893882Z 
2026-10-21T13:55:09.8894826Z 4.2 You agree to use the Google TV Add-on and write applications only for purposes that are permitted by (a) this License Agreement and (b) any applicable law, regulation or generally accepted practices or guidelines in the relevant jurisdictions (including any laws regarding the export of data or software to and from the United States or other relevant countries).
2026-10-21T13:55:09.8895875Z 
2026-10-21T13:55:09.8898971Z 4.3 You agree that if you use the Google TV Add-on to develop applications for general public users, you will protect the privacy and legal rights of those users. If the users provide you with user names, passwords, or other login information or personal information, your must make the users aware that the information will be available to your application, and you must provide legally adequate privacy notice and protection for those users. If your application stores personal or sensitive information provided by users, it must do so securely. If the user provides your application with Google Account information, your application may only use that information to access the user's Google Account when, and for the limited purposes for which, the user has given you explicit permission to do so.
2026-10-21T13:55:09.8901189Z 
2026-10-21T13:55:09.8902272Z 4.4 You agree that you will not engage in any activity with the Google TV Add-on, including the development or distribution of an application, that interferes with, disrupts, damages, or accesses in an unauthorized manner the servers, networks, or other properties or services of any third party including, but not limited to, Google, Multichannel Video Program Distributors or any mobile communications carrier.
2026-10-21T13:55:09.8904059Z 
2026-10-21T13:55:09.8905021Z 4.5 You agree that you are solely responsible for (and that Google has no responsibility to you or to any third party for) any data, content, or resources that you create, transmit or display through the Google TV platform and/or applications for the Google TV platform, and for the consequences of your actions (including any loss or damage which Google may suffer) by doing so.
2026-10-21T13:55:09.8906052Z 
2026-10-21T13:55:09.8907130Z 4.6 You agree that you are solely responsible for (and that Google has no responsibility to you or to any third party for) any breach of your obligations under this License Agreement, any applicable third party contract or Terms of Service, or any applicable law or regulation, and for the consequences (including any loss or damage which Google or any third party may suffer) of any such breach.
2026-10-21T13:55:09.8907811Z 
2026-10-21T13:55:09.8907872Z 5. Your Developer Credentials
2026-10-21T13:55:09.8907968Z 
2026-10-21T13:55:09.8908423Z 5.1 You agree that you are responsible for maintaining the confidentiality of any developer credentials that may be issued to you by Google or which you may choose yourself and that you will be solely responsible for all applications that are developed under your developer credentials.
2026-10-21T13:55:09.8908928Z 
2026-10-21T13:55:09.8908981Z 6. Privacy and Information
2026-10-21T13:55:09.8929966Z 
2026-10-21T13:55:09.8930860Z 6.1 In order to continually innovate and improve the Google TV Add-on, Google may collect certain usage statistics from the software including but not limited to a unique identifier, associated IP address, version number of the software, and information on which tools and/or services in the Google TV Add-on are being used and how they are being used. Before any of this information is collected, the Google TV Add-on will notify you and seek your consent. If you withhold consent, the information will not be collected.
2026-10-21T13:55:09.8932006Z 
2026-10-21T13:55:09.8932238Z 6.2 The data collected is examined in the aggregate to improve the Google TV Add-on and is maintained in accordance with Google's Privacy Policy.
2026-10-21T13:55:09.8932510Z 
2026-10-21T13:55:09.8932597Z 7. Third Party Applications for the Google TV Platform
2026-10-21T13:55:09.8932731Z 
2026-10-21T13:55:09.8933711Z 7.1 If you use the Google TV Add-on to run applications developed by a third party or that access data, content or resources provided by a third party, you agree that Google is not responsible for those applications, data, content, or resources. You understand that all data, content or resources which you may access through such third party applications are the sole responsibility of the person from which they originated and that Google is not liable for any loss or damage that you may experience as a result of the use or access of any of those third party applications, data, content, or resources.
2026-10-21T13:55:09.8934731Z 
2026-10-21T13:55:09.8935513Z 7.2 You should be aware the data, content, and resources presented to you through such a third party application may be protected by intellectual property rights which are owned by the providers (or by other persons or companies on their behalf). You may not modify, rent, lease, loan, sell, distribute or create derivative works based on these data, content, or resources (either in whole or in part) unless you have been specifically given permission to do so by the relevant owners.
2026-10-21T13:55:09.8936339Z 
2026-10-21T13:55:09.8936770Z 7.3 You acknowledge that your use of such third party applications, data, content, or resources may be subject to separate terms between you and the relevant third party. In that case, this License Agreement does not affect your legal relationship with these third parties.
2026-10-21T13:55:09.8937504Z 
2026-10-21T13:55:09.8937556Z 8. Using Google TV APIs
2026-10-21T13:55:09.8937729Z 
2026-10-21T13:55:09.8939317Z 8.1 If you use any Google TV API to retrieve data from Google, you acknowledge that the data (“Google TV API Content”) may be protected by intellectual property rights which are owned by Google or those parties that provide the data (or by other persons or companies on their behalf). Your use of any such API may be subject to additional Terms of Service. You may not modify, rent, lease, loan, sell, distribute or create derivative works based on this data (either in whole or in part) unless allowed by the relevant Terms of Service. Some portions of the Google TV API Content are licensed to Google by third parties, including but not limited to Tribune Media Services
2026-10-21T13:55:09.8940442Z 
2026-10-21T13:55:09.8940840Z 8.2 If you use any API to retrieve a user's data from Google, you acknowledge and agree that you shall retrieve data only with the user's explicit consent and only when, and for the limited purposes for which, the user has given you permission to do so.
2026-10-21T13:55:09.8941290Z 
2026-10-21T13:55:09.8941444Z 8.3 Except as explicitly permitted in Section 3 (Google TV Add-on License from Google), you must:
2026-10-21T13:55:09.8941639Z 
2026-10-21T13:55:09.8941930Z (a) not modify nor format the Google TV API Content except to the extent reasonably and technically necessary to optimize the display such Google TV API Content in your application;
2026-10-21T13:55:09.8942264Z 
2026-10-21T13:55:09.8942581Z (b) not edit the Google TV API Content in a manner that renders the Google TV API Content inaccurate of alters its inherent meaning (provided that displaying excerpts will not violate the foregoing); or
2026-10-21T13:55:09.8943031Z 
2026-10-21T13:55:09.8943178Z (c) not create any commercial audience measurement tool or service using the Google TV API Content
2026-10-21T13:55:09.8943377Z 
2026-10-21T13:55:09.8943441Z 9. Terminating this License Agreement
2026-10-21T13:55:09.8943546Z 
2026-10-21T13:55:09.8943713Z 9.1 This License Agreement will continue to apply until terminated by either you or Google as set out below.
2026-10-21T13:55:09.8943924Z 
2026-10-21T13:55:09.8944148Z 9.2 If you want to terminate this License Agreement, you may do so by ceasing your use of the Google TV Add-on and any relevant developer credentials.
2026-10-21T13:55:09.8944418Z 
2026-10-21T13:55:09.8944519Z 9.3 Google may at any time, terminate this License Agreement with you if:
2026-10-21T13:55:09.8944672Z 
2026-10-21T13:55:09.8944763Z (A) you have breached any provision of this License Agreement; or
2026-10-21T13:55:09.8944904Z 
2026-10-21T13:55:09.8944966Z (B) Google is required to do so by law; or
2026-10-21T13:55:09.8945075Z 
2026-10-21T13:55:09.8945399Z (C) the partner with whom Google offered certain parts of Google TV Add-on (such as APIs) to you has terminated its relationship with Google or ceased to offer certain parts of the Google TV Add-on to you; or
2026-10-21T13:55:09.8945762Z 
2026-10-21T13:55:09.8946298Z (D) Google decides to no longer providing the Google TV Add-on or certain parts of the Google TV Add-on to users in the country in which you are resident or from which you use the service, or the provision of the Google TV Add-on or certain Google TV Add-on services to you by Google is, in Google's sole discretion, no longer commercially viable.
2026-10-21T13:55:09.8947032Z 
2026-10-21T13:55:09.8947748Z 9.4 When this License Agreement comes to an end, all of the legal rights, obligations and liabilities that you and Google have benefited from, been subject to (or which have accrued over time whilst this License Agreement has been in force) or which are expressed to continue indefinitely, shall be unaffected by this cessation, and the provisions of paragraph 14.7 shall continue to apply to such rights, obligations and liabilities indefinitely.
2026-10-21T13:55:09.8948596Z 
2026-10-21T13:55:09.8948655Z 10. DISCLAIMER OF WARRANTIES
2026-10-21T13:55:09.8948744Z 
2026-10-21T13:55:09.8949055Z 10.1 YOU EXPRESSLY UNDERSTAND AND AGREE THAT YOUR USE OF THE GOOGLE TV ADD-ON IS AT YOUR SOLE RISK AND THAT THE GOOGLE TV ADD-ON IS PROVIDED "AS IS" AND "AS AVAILABLE" WITHOUT WARRANTY OF ANY KIND FROM GOOGLE.
2026-10-21T13:55:09.8949406Z 
2026-10-21T13:55:09.8949860Z 10.2 YOUR USE OF THE GOOGLE TV ADD-ON AND ANY MATERIAL DOWNLOADED OR OTHERWISE OBTAINED THROUGH THE USE OF THE GOOGLE TV ADD-ON IS AT YOUR OWN DISCRETION AND RISK AND YOU ARE SOLELY RESPONSIBLE FOR ANY DAMAGE TO YOUR COMPUTER SYSTEM OR OTHER DEVICE OR LOSS OF DATA THAT RESULTS FROM SUCH USE.
2026-10-21T13:55:09.8950359Z 
2026-10-21T13:55:09.8950773Z 10.3 GOOGLE FURTHER EXPRESSLY DISCLAIMS ALL WARRANTIES AND CONDITIONS OF ANY KIND, WHETHER EXPRESS OR IMPLIED, INCLUDING, BUT NOT LIMITED TO THE IMPLIED WARRANTIES AND CONDITIONS OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT.
2026-10-21T13:55:09.8951237Z 
2026-10-21T13:55:09.8951295Z 11. LIMITATION OF LIABILITY
2026-10-21T13:55:09.8951379Z 
2026-10-21T13:55:09.8952087Z 11.1 YOU EXPRESSLY UNDERSTAND AND AGREE THAT GOOGLE, ITS SUBSIDIARIES AND AFFILIATES, AND ITS LICENSORS SHALL NOT BE LIABLE TO YOU UNDER ANY THEORY OF LIABILITY FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL CONSEQUENTIAL OR EXEMPLARY DAMAGES THAT MAY BE INCURRED BY YOU, INCLUDING ANY LOSS OF DATA, WHETHER OR NOT GOOGLE OR ITS REPRESENTATIVES HAVE BEEN ADVISED OF OR SHOULD HAVE BEEN AWARE OF THE POSSIBILITY OF ANY SUCH LOSSES ARISING.
2026-10-21T13:55:09.8952831Z 
2026-10-21T13:55:09.8952879Z 12. Indemnification
2026-10-21T13:55:09.8952953Z 
2026-10-21T13:55:09.8954166Z 12.1 To the maximum extent permitted by law, you agree to defend, indemnify and hold harmless Google, its affiliates and their respective directors, officers, employees and agents from and against any and all claims, actions, suits or proceedings, as well as any and all losses, liabilities, damages, costs and expenses (including reasonable attorneys fees) arising out of or accruing from (a) your use of the Google TV Add-on, (b) any application you develop on the Google TV Add-on that infringes any copyright, trademark, trade secret, trade dress, patent or other intellectual property right of any person or defames any person or violates their rights of publicity or privacy, and (c) any non-compliance by you with this License Agreement.
2026-10-21T13:55:09.8955497Z 
2026-10-21T13:55:09.8955554Z 13. Changes to the License Agreement
2026-10-21T13:55:09.8955653Z 
2026-10-21T13:55:09.8955815Z 13.1 Google may make changes to the License Agreement as it distributes new versions of the Google TV Add-on.
2026-10-21T13:55:09.8956029Z 
2026-10-21T13:55:09.8956080Z 14. General Legal Terms
2026-10-21T13:55:09.8956160Z 
2026-10-21T13:55:09.8956688Z 14.1 This License Agreement constitute the whole legal agreement between you and Google and govern your use of the Google TV Add-on (excluding any services which Google may provide to you under a separate written agreement), and completely replace any prior agreements between you and Google in relation to the Google TV Add-on.
2026-10-21T13:55:09.8957394Z 
2026-10-21T13:55:09.8957901Z 14.2 You agree that if Google does not exercise or enforce any legal right or remedy which is contained in this License Agreement (or which Google has the benefit of under any applicable law), this will not be taken to be a formal waiver of Google's rights and that those rights or remedies will still be available to Google.
2026-10-21T13:55:09.8958447Z 
2026-10-21T13:55:09.8958998Z 14.3 If any court of law, having the jurisdiction to decide on this matter, rules that any provision of this License Agreement is invalid, then that provision will be removed from this License Agreement without affecting the rest of this License Agreement. The remaining provisions of this License Agreement will continue to be valid and enforceable.
2026-10-21T13:55:09.8959599Z 
2026-10-21T13:55:09.8960673Z 14.4 You acknowledge and agree that Google’s API data licensors and each member of the group of companies of which Google is the parent shall be third party beneficiaries to this License Agreement and that such other companies shall be entitled to directly enforce, and rely upon, any provision of this License Agreement that confers a benefit on (or rights in favor of) them. Other than this, no other person or company shall be third party beneficiaries to this License Agreement.
2026-10-21T13:55:09.8961487Z 
2026-10-21T13:55:09.8961952Z 14.5 EXPORT RESTRICTIONS. THE GOOGLE TV ADD-ON IS SUBJECT TO UNITED STATES EXPORT LAWS AND REGULATIONS. YOU MUST COMPLY WITH ALL DOMESTIC AND INTERNATIONAL EXPORT LAWS AND REGULATIONS THAT APPLY TO THE GOOGLE TV ADD-ON. THESE LAWS INCLUDE RESTRICTIONS ON DESTINATIONS, END USERS AND END USE.
2026-10-21T13:55:09.8962457Z 
2026-10-21T13:55:09.8963005Z 14.6 The rights granted in this License Agreement may not be assigned or transferred by either you or Google without the prior written approval of the other party. Neither you nor Google shall be permitted to delegate their responsibilities or obligations under this License Agreement without the prior written approval of the other party.
2026-10-21T13:55:09.8963605Z 
2026-10-21T13:55:09.8964517Z 14.7 This License Agreement, and your relationship with Google under this License Agreement, shall be governed by the laws of the State of California without regard to its conflict of laws provisions. You and Google agree to submit to the exclusive jurisdiction of the courts located within the county of Santa Clara, California to resolve any legal matter arising from this License Agreement. Notwithstanding this, you agree that Google shall still be allowed to apply for injunctive remedies (or an equivalent type of urgent legal relief) in any jurisdiction.
2026-10-21T13:55:09.8965466Z 
2026-10-21T13:55:09.8965468Z 
2026-10-21T13:55:09.8965606Z August 15, 2011
2026-10-21T13:55:09.8965735Z ---------------------------------------
2026-10-21T13:55:09.8965919Z Accept? (y/N): 
2026-10-21T13:55:09.8966055Z 2/7: License android-googlexr-license:
2026-10-21T13:55:09.8966212Z ---------------------------------------
2026-10-21T13:55:09.8966718Z To get started with the Android XR Emulator System Image SDK, you must agree to the following terms and conditions. As described below, please note that this is a preview, emulated version of the Android XR OS, subject to change, that you use at your own risk.
2026-10-21T13:55:09.8967245Z 
2026-10-21T13:55:09.8967342Z This is the Android XR Emulator System Image SDK License Agreement
2026-10-21T13:55:09.8967488Z 
2026-10-21T13:55:09.8967533Z 1. Introduction
2026-10-21T13:55:09.8967605Z 
2026-10-21T13:55:09.8968314Z 1.1 The Android Android XR Emulator System Image SDK (referred to in the License Agreement as the "SDK" and specifically including the Android system files,, packaged APIs, library files (if and when they are made available), and Google applications and APIs add-ons) is licensed to you subject to the terms of the License Agreement. The License Agreement forms a legally binding contract between you and Google in relation to your use of the SDK.
2026-10-21T13:55:09.8969074Z 
2026-10-21T13:55:09.8969433Z 1.2 "Android" means the Android software stack for devices, as made available under the Android Open Source Project, which is located at the following URL: https://source.android.com/, as updated from time to time.
2026-10-21T13:55:09.8969818Z 
2026-10-21T13:55:09.8970152Z 1.3 "Google" means Google LLC, organized under the laws of the State of Delaware, USA, and operating under the laws of the USA with principal place of business at 1600 Amphitheatre Parkway, Mountain View, CA 94043, USA.
2026-10-21T13:55:09.8970535Z 
2026-10-21T13:55:09.8970592Z 2. Accepting this License Agreement
2026-10-21T13:55:09.8970690Z 
2026-10-21T13:55:09.8970903Z 2.1 In order to use the SDK, you must first agree to the License Agreement. You may not use the SDK if you do not accept the License Agreement.
2026-10-21T13:55:09.8971414Z 
2026-10-21T13:55:09.8971641Z 2.2 By clicking to accept and/or using this SDK, you hereby agree to the terms of the License Agreement.
2026-10-21T13:55:09.8971970Z 
2026-10-21T13:55:09.8972407Z 2.3 You may not use the SDK and may not accept the License Agreement if you are a person barred from receiving the SDK under the laws of the United States or other countries, including the country in which you are resident or from which you use the SDK.
2026-10-21T13:55:09.8972844Z 
2026-10-21T13:55:09.8973420Z 2.4 If you are agreeing to be bound by the License Agreement on behalf of your employer or other entity, you represent and warrant that you have full legal authority to bind your employer or such entity to the License Agreement. If you do not have the requisite authority, you may not accept the License Agreement or use the SDK on behalf of your employer or other entity.
2026-10-21T13:55:09.8974042Z 
2026-10-21T13:55:09.8974103Z 3. SDK License from Google
2026-10-21T13:55:09.8974190Z 
2026-10-21T13:55:09.8974564Z 3.1 Subject to the terms of the License Agreement, Google grants you a limited, worldwide, royalty-free, non-assignable, non-exclusive, and non-sublicensable license to use the SDK solely to develop applications for Android XR.
2026-10-21T13:55:09.8974982Z 
2026-10-21T13:55:09.8975326Z 3.2 You may not use this SDK to develop applications for other platforms or to develop another SDK. You are of course free to develop applications for other platforms provided that this SDK is not used for that purpose.
2026-10-21T13:55:09.8975714Z 
2026-10-21T13:55:09.8976343Z 3.3 You agree that Google or third parties own all legal right, title and interest in and to the SDK, including any Intellectual Property Rights that subsist in the SDK. "Intellectual Property Rights" means any and all rights under patent law, copyright law, trade secret law, trademark law, and any and all other proprietary rights. Google reserves all rights not expressly granted to you.
2026-10-21T13:55:09.8977269Z 
2026-10-21T13:55:09.8978206Z 3.4 You may not use the SDK for any purpose not expressly permitted by the License Agreement. Except to the extent required by applicable third party licenses, you may not (a) copy (except for backup purposes), modify, adapt, redistribute, decompile, reverse engineer, disassemble, or create derivative works of the SDK or any part of the SDK; or (b) load any part of the SDK onto a mobile handset or any other hardware device except a personal computer, combine any part of the SDK with other software, or distribute any software or device incorporating a part of the SDK.
2026-10-21T13:55:09.8979169Z 
2026-10-21T13:55:09.8979839Z 3.5 Use, reproduction and distribution of components of the SDK licensed under an open source software license are governed solely by the terms of that open source software license and not the License Agreement. You agree to remain a licensee in good standing in regard to such open source software licenses under all the rights granted and to refrain from any actions that may terminate, suspend, or breach such rights.
2026-10-21T13:55:09.8980562Z 
2026-10-21T13:55:09.8981230Z 3.6 You agree that the form and nature of the SDK that Google provides may change without prior notice to you and that future versions of the SDK may be incompatible with applications developed on previous versions of the SDK. You agree that Google may stop (permanently or temporarily) providing the SDK (or any features within the SDK) to you or to users generally at Google's sole discretion, without prior notice to you.
2026-10-21T13:55:09.8981939Z 
2026-10-21T13:55:09.8982204Z 3.7 Nothing in the License Agreement gives you a right to use any of Google's trade names, trademarks, service marks, logos, domain names, or other distinctive brand features.
2026-10-21T13:55:09.8982520Z 
2026-10-21T13:55:09.8982809Z 3.8 You agree that you will not remove, obscure, or alter any proprietary rights notices (including copyright and trademark notices) that may be affixed to or contained within the SDK.
2026-10-21T13:55:09.8983141Z 
2026-10-21T13:55:09.8983278Z 4. Use of the SDK by You
2026-10-21T13:55:09.8983363Z 
2026-10-21T13:55:09.8983867Z 4.1 Google agrees that it obtains no right, title or interest from you (or your licensors) under the License Agreement in or to any software applications that you develop using the SDK, including any intellectual property rights that subsist in those applications.
2026-10-21T13:55:09.8984604Z 
2026-10-21T13:55:09.8985515Z 4.2 You agree to use the SDK and write applications only for purposes that are permitted by (a) the License Agreement and (b) any applicable law, regulation or generally accepted practices or guidelines in the relevant jurisdictions (including any laws regarding the export of data or software to and from the United States or other relevant countries).
2026-10-21T13:55:09.8986430Z 
2026-10-21T13:55:09.8998574Z 4.3 You agree that if you use the SDK to develop applications for general public users, you will protect the privacy and legal rights of those users. If the users provide you with user names, passwords, or other login information or personal information, you must make the users aware that the information will be available to your application, and you must provide legally adequate privacy notice and protection for those users. If your application stores personal or sensitive information provided by users, it must do so securely. If the user provides your application with Google Account information, your application may only use that information to access the user's Google Account when, and for the limited purposes for which, the user has given you permission to do so.
2026-10-21T13:55:09.9000769Z 
2026-10-21T13:55:09.9001713Z 4.4 You agree that you will not engage in any activity with the SDK, including the development or distribution of an application, that interferes with, disrupts, damages, or accesses in an unauthorized manner the servers, networks, or other properties or services of any third party including, but not limited to, Google or any mobile communications carrier.
2026-10-21T13:55:09.9002950Z 
2026-10-21T13:55:09.9003855Z 4.5 You agree that you are solely responsible for (and that Google has no responsibility to you or to any third party for) any data, content, or resources that you create, transmit or display through Android and/or applications for Android, and for the consequences of your actions (including any loss or damage which Google may suffer) by doing so.
2026-10-21T13:55:09.9004833Z 
2026-10-21T13:55:09.9005863Z 4.6 You agree that you are solely responsible for (and that Google has no responsibility to you or to any third party for) any breach of your obligations under the License Agreement, any applicable third party contract or Terms of Service, or any applicable law or regulation, and for the consequences (including any loss or damage which Google or any third party may suffer) of any such breach.
2026-10-21T13:55:09.9007076Z 
2026-10-21T13:55:09.9007854Z 4.7 The SDK is in development, and your testing and feedback are an important part of the development process. By using the SDK, you acknowledge that implementation of some features are still under development and that you should not rely on the SDK having the full functionality of a stable release.
2026-10-21T13:55:09.9008707Z 
2026-10-21T13:55:09.9008797Z 5. Your Developer Credentials
2026-10-21T13:55:09.9008940Z 
2026-10-21T13:55:09.9009683Z 5.1 You agree that you are responsible for maintaining the confidentiality of any developer credentials that may be issued to you by Google or which you may choose yourself and that you will be solely responsible for all applications that are developed under your developer credentials.
2026-10-21T13:55:09.9010515Z 
2026-10-21T13:55:09.9010596Z 6. Privacy and Information
2026-10-21T13:55:09.9010730Z 
2026-10-21T13:55:09.9012009Z 6.1 In order to continually innovate and improve the SDK, Google may collect certain usage statistics from the software including but not limited to a unique identifier, associated IP address, version number of the software, and information on which tools and/or services in the SDK are being used and how they are being used. Before any of this information is collected, the SDK will notify you and seek your consent. If you withhold consent, the information will not be collected.
2026-10-21T13:55:09.9013451Z 
2026-10-21T13:55:09.9013992Z 6.2 The data collected is examined in the aggregate to improve the SDK and is maintained in accordance with Google's Privacy Policy, which is located at the following URL: https://policies.google.com/privacy
2026-10-21T13:55:09.9014599Z 
2026-10-21T13:55:09.9014835Z 6.3 Anonymized and aggregated sets of the data may be shared with Google partners to improve the SDK.
2026-10-21T13:55:09.9015151Z 
2026-10-21T13:55:09.9015237Z 7. Third Party Applications
2026-10-21T13:55:09.9015371Z 
2026-10-21T13:55:09.9017021Z 7.1 If you use the SDK to run applications developed by a third party or that access data, content or resources provided by a third party, you agree that Google is not responsible for those applications, data, content, or resources. You understand that all data, content or resources which you may access through such third party applications are the sole responsibility of the person from which they originated and that Google is not liable for any loss or damage that you may experience as a result of the use or access of any of those third party applications, data, content, or resources.
2026-10-21T13:55:09.9018643Z 
2026-10-21T13:55:09.9019924Z 7.2 You should be aware that the data, content, and resources presented to you through such a third party application may be protected by intellectual property rights which are owned by the providers (or by other persons or companies on their behalf). You may not modify, rent, lease, loan, sell, distribute or create derivative works based on these data, content, or resources (either in whole or in part) unless you have been specifically given permission to do so by the relevant owners.
2026-10-21T13:55:09.9021362Z 
2026-10-21T13:55:09.9022057Z 7.3 You acknowledge that your use of such third party applications, data, content, or resources may be subject to separate terms between you and the relevant third party. In that case, the License Agreement does not affect your legal relationship with these third parties.
2026-10-21T13:55:09.9022829Z 
2026-10-21T13:55:09.9022956Z 8. Using Android APIs
2026-10-21T13:55:09.9023078Z 
2026-10-21T13:55:09.9023151Z 8.1 Google Data APIs
2026-10-21T13:55:09.9023271Z 
2026-10-21T13:55:09.9024556Z 8.1.1 If you use any API to retrieve data from Google, you acknowledge that the data may be protected by intellectual property rights which are owned by Google or those parties that provide the data (or by other persons or companies on their behalf). Your use of any such API may be subject to additional Terms of Service. You may not modify, rent, lease, loan, sell, distribute or create derivative works based on this data (either in whole or in part) unless allowed by the relevant Terms of Service.
2026-10-21T13:55:09.9025915Z 
2026-10-21T13:55:09.9028290Z 8.1.2 If you use any API to retrieve a user's data from Google, you acknowledge and agree that you shall retrieve data only with the user's explicit consent and only when, and for the limited purposes for which, the user has given you permission to do so. If you use the Android Recognition Service API, documented at the following URL: https://developer.android.com/reference/android/speech/RecognitionService, as updated from time to time, you acknowledge that the use of the API is subject to the Data Processing Addendum for Products where Google is a Data Processor, which is located at the following URL: https://privacy.google.com/businesses/gdprprocessorterms/, as updated from time to time. By clicking to accept, you hereby agree to the terms of the Data Processing Addendum for Products where Google is a Data Processor.
2026-10-21T13:55:09.9030608Z 
2026-10-21T13:55:09.9030706Z 9. Terminating this License Agreement
2026-10-21T13:55:09.9030867Z 
2026-10-21T13:55:09.9031131Z 9.1 The License Agreement will continue to apply until terminated by either you or Google as set out below.
2026-10-21T13:55:09.9031574Z 
2026-10-21T13:55:09.9031897Z 9.2 If you want to terminate the License Agreement, you may do so by ceasing your use of the SDK and any relevant developer credentials.
2026-10-21T13:55:09.9032298Z 
2026-10-21T13:55:09.9033995Z 9.3 Google may at any time, terminate the License Agreement with you if: (A) you have breached any provision of the License Agreement; or (B) Google is required to do so by law; or (C) the partner with whom Google offered certain parts of SDK (such as APIs) to you has terminated its relationship with Google or ceased to offer certain parts of the SDK to you; or (D) Google decides to no longer provide the SDK or certain parts of the SDK to users in the country in which you are resident or from which you use the service, or the provision of the SDK or certain SDK services to you by Google is, in Google's sole discretion, no longer commercially viable.
2026-10-21T13:55:09.9035739Z 
2026-10-21T13:55:09.9037013Z 9.4 When the License Agreement comes to an end, all of the legal rights, obligations and liabilities that you and Google have benefited from, been subject to (or which have accrued over time whilst the License Agreement has been in force) or which are expressed to continue indefinitely, shall be unaffected by this cessation, and the provisions of paragraph 14.7 shall continue to apply to such rights, obligations and liabilities indefinitely.
2026-10-21T13:55:09.9038253Z 
2026-10-21T13:55:09.9038337Z 10. DISCLAIMER OF WARRANTIES
2026-10-21T13:55:09.9038474Z 
2026-10-21T13:55:09.9038909Z 10.1 YOU EXPRESSLY UNDERSTAND AND AGREE THAT YOUR USE OF THE SDK IS AT YOUR SOLE RISK AND THAT THE SDK IS PROVIDED "AS IS" AND "AS AVAILABLE" WITHOUT WARRANTY OF ANY KIND FROM GOOGLE.
2026-10-21T13:55:09.9039422Z 
2026-10-21T13:55:09.9040091Z 10.2 YOUR USE OF THE SDK AND ANY MATERIAL DOWNLOADED OR OTHERWISE OBTAINED THROUGH THE USE OF THE SDK IS AT YOUR OWN DISCRETION AND RISK AND YOU ARE SOLELY RESPONSIBLE FOR ANY DAMAGE TO YOUR COMPUTER SYSTEM OR OTHER DEVICE OR LOSS OF DATA THAT RESULTS FROM SUCH USE.
2026-10-21T13:55:09.9040935Z 
2026-10-21T13:55:09.9041615Z 10.3 GOOGLE FURTHER EXPRESSLY DISCLAIMS ALL WARRANTIES AND CONDITIONS OF ANY KIND, WHETHER EXPRESS OR IMPLIED, INCLUDING, BUT NOT LIMITED TO THE IMPLIED WARRANTIES AND CONDITIONS OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT.
2026-10-21T13:55:09.9042366Z 
2026-10-21T13:55:09.9042446Z 11. LIMITATION OF LIABILITY
2026-10-21T13:55:09.9042581Z 
2026-10-21T13:55:09.9043737Z 11.1 YOU EXPRESSLY UNDERSTAND AND AGREE THAT GOOGLE, ITS SUBSIDIARIES AND AFFILIATES, AND ITS LICENSORS SHALL NOT BE LIABLE TO YOU UNDER ANY THEORY OF LIABILITY FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, CONSEQUENTIAL OR EXEMPLARY DAMAGES THAT MAY BE INCURRED BY YOU, INCLUDING ANY LOSS OF DATA, WHETHER OR NOT GOOGLE OR ITS REPRESENTATIVES HAVE BEEN ADVISED OF OR SHOULD HAVE BEEN AWARE OF THE POSSIBILITY OF ANY SUCH LOSSES ARISING.
2026-10-21T13:55:09.9044963Z 
2026-10-21T13:55:09.9045044Z 12. Indemnification
2026-10-21T13:55:09.9045153Z 
2026-10-21T13:55:09.9047587Z 12.1 To the maximum extent permitted by law, you agree to defend, indemnify and hold harmless Google, its affiliates and their respective directors, officers, employees and agents from and against any and all claims, actions, suits or proceedings, as well as any and all losses, liabilities, damages, costs and expenses (including reasonable attorneys fees) arising out of or accruing from (a) your use of the SDK, (b) any application you develop on the SDK that infringes any copyright, trademark, trade secret, trade dress, patent or other intellectual property right of any person or defames any person or violates their rights of publicity or privacy, and (c) any non-compliance by you with the License Agreement.
2026-10-21T13:55:09.9049553Z 
2026-10-21T13:55:09.9049638Z 13. Changes to the License Agreement
2026-10-21T13:55:09.9049796Z 
2026-10-21T13:55:09.9050397Z 13.1 Google may make changes to the License Agreement as it distributes new versions of the SDK. When these changes are made, Google will make a new version of the License Agreement available on the website where the SDK is made available.
2026-10-21T13:55:09.9051182Z 
2026-10-21T13:55:09.9051262Z 14. General Legal Terms
2026-10-21T13:55:09.9051386Z 
2026-10-21T13:55:09.9052179Z 14.1 The License Agreement constitutes the whole legal agreement between you and Google and governs your use of the SDK (excluding any services which Google may provide to you under a separate written agreement), and completely replaces any prior agreements between you and Google in relation to the SDK.
2026-10-21T13:55:09.9053049Z 
2026-10-21T13:55:09.9053856Z 14.2 You agree that if Google does not exercise or enforce any legal right or remedy which is contained in the License Agreement (or which Google has the benefit of under any applicable law), this will not be taken to be a formal waiver of Google's rights and that those rights or remedies will still be available to Google.
2026-10-21T13:55:09.9054750Z 
2026-10-21T13:55:09.9055649Z 14.3 If any court of law, having the jurisdiction to decide on this matter, rules that any provision of the License Agreement is invalid, then that provision will be removed from the License Agreement without affecting the rest of the License Agreement. The remaining provisions of the License Agreement will continue to be valid and enforceable.
2026-10-21T13:55:09.9056619Z 
2026-10-21T13:55:09.9057883Z 14.4 You acknowledge and agree that each member of the group of companies of which Google is the parent shall be third party beneficiaries to the License Agreement and that such other companies shall be entitled to directly enforce, and rely upon, any provision of the License Agreement that confers a benefit on (or rights in favor of) them. Other than this, no other person or company shall be third party beneficiaries to the License Agreement.
2026-10-21T13:55:09.9059204Z 
2026-10-21T13:55:09.9059891Z 14.5 EXPORT RESTRICTIONS. THE SDK IS SUBJECT TO UNITED STATES EXPORT LAWS AND REGULATIONS. YOU MUST COMPLY WITH ALL DOMESTIC AND INTERNATIONAL EXPORT LAWS AND REGULATIONS THAT APPLY TO THE SDK. THESE LAWS INCLUDE RESTRICTIONS ON DESTINATIONS, END USERS AND END USE.
2026-10-21T13:55:09.9060648Z 
2026-10-21T13:55:09.9061544Z 14.6 The rights granted in the License Agreement may not be assigned or transferred by either you or Google without the prior written approval of the other party. Neither you nor Google shall be permitted to delegate their responsibilities or obligations under the License Agreement without the prior written approval of the other party.
2026-10-21T13:55:09.9062540Z 
2026-10-21T13:55:09.9064037Z 14.7 The License Agreement, and your relationship with Google under the License Agreement, shall be governed by the laws of the State of California without regard to its conflict of laws provisions. You and Google agree to submit to the exclusive jurisdiction of the courts located within the county of Santa Clara, California to resolve any legal matter arising from the License Agreement. Notwithstanding this, you agree that Google shall still be allowed to apply for injunctive remedies (or an equivalent type of urgent legal relief) in any jurisdiction.
2026-10-21T13:55:09.9065693Z ---------------------------------------
2026-10-21T13:55:09.9065924Z Accept? (y/N): 
2026-10-21T13:55:09.9066131Z 3/7: License android-sdk-arm-dbt-license:
2026-10-21T13:55:09.9066381Z ---------------------------------------
2026-10-21T13:55:09.9066602Z Terms and Conditions
2026-10-21T13:55:09.9066719Z 
2026-10-21T13:55:09.9066952Z This is the Android Software Development Kit License Agreement
2026-10-21T13:55:09.9067172Z 
2026-10-21T13:55:09.9067243Z 1. Introduction
2026-10-21T13:55:09.9067348Z 
2026-10-21T13:55:09.9068311Z 1.1 The Android Software Development Kit (referred to in the License Agreement as the "SDK" and specifically including the Android system files, packaged APIs, and Google APIs add-ons) is licensed to you subject to the terms of the License Agreement. The License Agreement forms a legally binding contract between you and Google in relation to your use of the SDK.
2026-10-21T13:55:09.9069442Z 
2026-10-21T13:55:09.9069997Z 1.2 "Android" means the Android software stack for devices, as made available under the Android Open Source Project, which is located at the following URL: http://source.android.com/, as updated from time to time.
2026-10-21T13:55:09.9070620Z 
2026-10-21T13:55:09.9071540Z 1.3 A "compatible implementation" means any Android device that (i) complies with the Android Compatibility Definition document, which can be found at the Android compatibility website (http://source.android.com/compatibility) and which may be updated from time to time; and (ii) successfully passes the Android Compatibility Test Suite (CTS).
2026-10-21T13:55:09.9072530Z 
2026-10-21T13:55:09.9072927Z 1.4 "Google" means Google Inc., a Delaware corporation with principal place of business at 1600 Amphitheatre Parkway, Mountain View, CA 94043, United States.
2026-10-21T13:55:09.9073407Z 
2026-10-21T13:55:09.9073410Z 
2026-10-21T13:55:09.9073502Z 2. Accepting the License Agreement
2026-10-21T13:55:09.9073650Z 
2026-10-21T13:55:09.9073985Z 2.1 In order to use the SDK, you must first agree to the License Agreement. You may not use the SDK if you do not accept the License Agreement.
2026-10-21T13:55:09.9074403Z 
2026-10-21T13:55:09.9074586Z 2.2 By clicking to accept, you hereby agree to the terms of the License Agreement.
2026-10-21T13:55:09.9074854Z 
2026-10-21T13:55:09.9075491Z 2.3 You may not use the SDK and may not accept the License Agreement if you are a person barred from receiving the SDK under the laws of the United States or other countries, including the country in which you are resident or from which you use the SDK.
2026-10-21T13:55:09.9076200Z 
2026-10-21T13:55:09.9077242Z 2.4 If you are agreeing to be bound by the License Agreement on behalf of your employer or other entity, you represent and warrant that you have full legal authority to bind your employer or such entity to the License Agreement. If you do not have the requisite authority, you may not accept the License Agreement or use the SDK on behalf of your employer or other entity.
2026-10-21T13:55:09.9078371Z 
2026-10-21T13:55:09.9078374Z 
2026-10-21T13:55:09.9078454Z 3. SDK License from Google
2026-10-21T13:55:09.9078585Z 
2026-10-21T13:55:09.9079270Z 3.1 Subject to the terms of the License Agreement, Google grants you a limited, worldwide, royalty-free, non-assignable, non-exclusive, and non-sublicensable license to use the SDK solely to develop applications for compatible implementations of Android.
2026-10-21T13:55:09.9080024Z 
2026-10-21T13:55:09.9080908Z 3.2 You may not use this SDK to develop applications for other platforms (including non-compatible implementations of Android) or to develop another SDK. You are of course free to develop applications for other platforms, including non-compatible implementations of Android, provided that this SDK is not used for that purpose.
2026-10-21T13:55:09.9081868Z 
2026-10-21T13:55:09.9082893Z 3.3 You agree that Google or third parties own all legal right, title and interest in and to the SDK, including any Intellectual Property Rights that subsist in the SDK. "Intellectual Property Rights" means any and all rights under patent law, copyright law, trade secret law, trademark law, and any and all other proprietary rights. Google reserves all rights not expressly granted to you.
2026-10-21T13:55:09.9083976Z 
2026-10-21T13:55:09.9084856Z 3.4 You may not use the SDK for any purpose not expressly permitted by the License Agreement. Except to the extent required by applicable third party licenses, you may not copy (except for backup purposes), modify, adapt, redistribute, decompile, reverse engineer, disassemble, or create derivative works of the SDK or any part of the SDK.
2026-10-21T13:55:09.9085815Z 
2026-10-21T13:55:09.9086349Z 3.5 Use, reproduction and distribution of components of the SDK licensed under an open source software license are governed solely by the terms of that open source software license and not the License Agreement.
2026-10-21T13:55:09.9087153Z 
2026-10-21T13:55:09.9087832Z 3.6 You agree that the form and nature of the SDK that Google provides may change without prior notice to you and that future versions of the SDK may be incompatible with applications developed on previous versions of the SDK. You agree that Google may stop (permanently or temporarily) providing the SDK (or any features within the SDK) to you or to users generally at Google's sole discretion, without prior notice to you.
2026-10-21T13:55:09.9088550Z 
2026-10-21T13:55:09.9088816Z 3.7 Nothing in the License Agreement gives you a right to use any of Google's trade names, trademarks, service marks, logos, domain names, or other distinctive brand features.
2026-10-21T13:55:09.9089131Z 
2026-10-21T13:55:09.9089419Z 3.8 You agree that you will not remove, obscure, or alter any proprietary rights notices (including copyright and trademark notices) that may be affixed to or contained within the SDK.
2026-10-21T13:55:09.9089759Z 
2026-10-21T13:55:09.9089762Z 
2026-10-21T13:55:09.9089817Z 4. Use of the SDK by You
2026-10-21T13:55:09.9089899Z 
2026-10-21T13:55:09.9090313Z 4.1 Google agrees that it obtains no right, title or interest from you (or your licensors) under the License Agreement in or to any software applications that you develop using the SDK, including any intellectual property rights that subsist in those applications.
2026-10-21T13:55:09.9090777Z 
2026-10-21T13:55:09.9091337Z 4.2 You agree to use the SDK and write applications only for purposes that are permitted by (a) the License Agreement and (b) any applicable law, regulation or generally accepted practices or guidelines in the relevant jurisdictions (including any laws regarding the export of data or software to and from the United States or other relevant countries).
2026-10-21T13:55:09.9091942Z 
2026-10-21T13:55:09.9093212Z 4.3 You agree that if you use the SDK to develop applications for general public users, you will protect the privacy and legal rights of those users. If the users provide you with user names, passwords, or other login information or personal information, you must make the users aware that the information will be available to your application, and you must provide legally adequate privacy notice and protection for those users. If your application stores personal or sensitive information provided by users, it must do so securely. If the user provides your application with Google Account information, your application may only use that information to access the user's Google Account when, and for the limited purposes for which, the user has given you permission to do so.
2026-10-21T13:55:09.9094634Z 
2026-10-21T13:55:09.9095210Z 4.4 You agree that you will not engage in any activity with the SDK, including the development or distribution of an application, that interferes with, disrupts, damages, or accesses in an unauthorized manner the servers, networks, or other properties or services of any third party including, but not limited to, Google or any mobile communications carrier.
2026-10-21T13:55:09.9095833Z 
2026-10-21T13:55:09.9096384Z 4.5 You agree that you are solely responsible for (and that Google has no responsibility to you or to any third party for) any data, content, or resources that you create, transmit or display through Android and/or applications for Android, and for the consequences of your actions (including any loss or damage which Google may suffer) by doing so.
2026-10-21T13:55:09.9097156Z 
2026-10-21T13:55:09.9097778Z 4.6 You agree that you are solely responsible for (and that Google has no responsibility to you or to any third party for) any breach of your obligations under the License Agreement, any applicable third party contract or Terms of Service, or any applicable law or regulation, and for the consequences (including any loss or damage which Google or any third party may suffer) of any such breach.
2026-10-21T13:55:09.9098452Z 
2026-10-21T13:55:09.9099397Z 4.7 This software enables the execution of intellectual property owned by Arm Limited. You agree that your use of the software, that allows execution of ARM Instruction Set Architecture (“ISA”) compliant executables for application development and debug only on x86 desktop, laptop, customer on-premise servers, and customer-procured cloud-based environments.
2026-10-21T13:55:09.9100228Z 
2026-10-21T13:55:09.9100289Z 5. Your Developer Credentials
2026-10-21T13:55:09.9100381Z 
2026-10-21T13:55:09.9100837Z 5.1 You agree that you are responsible for maintaining the confidentiality of any developer credentials that may be issued to you by Google or which you may choose yourself and that you will be solely responsible for all applications that are developed under your developer credentials.
2026-10-21T13:55:09.9101339Z 
2026-10-21T13:55:09.9101393Z 6. Privacy and Information
2026-10-21T13:55:09.9101481Z 
2026-10-21T13:55:09.9102253Z 6.1 In order to continually innovate and improve the SDK, Google may collect certain usage statistics from the software including but not limited to a unique identifier, associated IP address, version number of the software, and information on which tools and/or services in the SDK are being used and how they are being used. Before any of this information is collected, the SDK will notify you and seek your consent. If you withhold consent, the information will not be collected.
2026-10-21T13:55:09.9103081Z 
2026-10-21T13:55:09.9103278Z 6.2 The data collected is examined in the aggregate to improve the SDK and is maintained in accordance with Google's Privacy Policy.
2026-10-21T13:55:09.9103525Z 
2026-10-21T13:55:09.9103527Z 
2026-10-21T13:55:09.9103581Z 7. Third Party Applications
2026-10-21T13:55:09.9103672Z 
2026-10-21T13:55:09.9104623Z 7.1 If you use the SDK to run applications developed by a third party or that access data, content or resources provided by a third party, you agree that Google is not responsible for those applications, data, content, or resources. You understand that all data, content or resources which you may access through such third party applications are the sole responsibility of the person from which they originated and that Google is not liable for any loss or damage that you may experience as a result of the use or access of any of those third party applications, data, content, or resources.
2026-10-21T13:55:09.9105702Z 
2026-10-21T13:55:09.9106476Z 7.2 You should be aware the data, content, and resources presented to you through such a third party application may be protected by intellectual property rights which are owned by the providers (or by other persons or companies on their behalf). You may not modify, rent, lease, loan, sell, distribute or create derivative works based on these data, content, or resources (either in whole or in part) unless you have been specifically given permission to do so by the relevant owners.
2026-10-21T13:55:09.9107521Z 
2026-10-21T13:55:09.9107954Z 7.3 You acknowledge that your use of such third party applications, data, content, or resources may be subject to separate terms between you and the relevant third party. In that case, the License Agreement does not affect your legal relationship with these third parties.
2026-10-21T13:55:09.9108437Z 
2026-10-21T13:55:09.9108441Z 
2026-10-21T13:55:09.9108495Z 8. Using Android APIs
2026-10-21T13:55:09.9108584Z 
2026-10-21T13:55:09.9108630Z 8.1 Google Data APIs
2026-10-21T13:55:09.9108706Z 
2026-10-21T13:55:09.9109496Z 8.1.1 If you use any API to retrieve data from Google, you acknowledge that the data may be protected by intellectual property rights which are owned by Google or those parties that provide the data (or by other persons or companies on their behalf). Your use of any such API may be subject to additional Terms of Service. You may not modify, rent, lease, loan, sell, distribute or create derivative works based on this data (either in whole or in part) unless allowed by the relevant Terms of Service.
2026-10-21T13:55:09.9110343Z 
2026-10-21T13:55:09.9111717Z 8.1.2 If you use any API to retrieve a user's data from Google, you acknowledge and agree that you shall retrieve data only with the user's explicit consent and only when, and for the limited purposes for which, the user has given you permission to do so. If you use the Android Recognition Service API, documented at the following URL: https://developer.android.com/reference/android/speech/RecognitionService, as updated from time to time, you acknowledge that the use of the API is subject to the Data Processing Addendum for Products where Google is a Data Processor, which is located at the following URL: https://privacy.google.com/businesses/gdprprocessorterms/, as updated from time to time. By clicking to accept, you hereby agree to the terms of the Data Processing Addendum for Products where Google is a Data Processor.
2026-10-21T13:55:09.9113199Z 
2026-10-21T13:55:09.9113201Z 
2026-10-21T13:55:09.9113262Z 9. Terminating the License Agreement
2026-10-21T13:55:09.9113357Z 
2026-10-21T13:55:09.9113524Z 9.1 The License Agreement will continue to apply until terminated by either you or Google as set out below.
2026-10-21T13:55:09.9113736Z 
2026-10-21T13:55:09.9113930Z 9.2 If you want to terminate the License Agreement, you may do so by ceasing your use of the SDK and any relevant developer credentials.
2026-10-21T13:55:09.9114174Z 
2026-10-21T13:55:09.9115195Z 9.3 Google may at any time, terminate the License Agreement with you if: (A) you have breached any provision of the License Agreement; or (B) Google is required to do so by law; or (C) the partner with whom Google offered certain parts of SDK (such as APIs) to you has terminated its relationship with Google or ceased to offer certain parts of the SDK to you; or (D) Google decides to no longer provide the SDK or certain parts of the SDK to users in the country in which you are resident or from which you use the service, or the provision of the SDK or certain SDK services to you by Google is, in Google's sole discretion, no longer commercially viable.
2026-10-21T13:55:09.9116338Z 
2026-10-21T13:55:09.9117159Z 9.4 When the License Agreement comes to an end, all of the legal rights, obligations and liabilities that you and Google have benefited from, been subject to (or which have accrued over time whilst the License Agreement has been in force) or which are expressed to continue indefinitely, shall be unaffected by this cessation, and the provisions of paragraph 14.7 shall continue to apply to such rights, obligations and liabilities indefinitely.
2026-10-21T13:55:09.9117911Z 
2026-10-21T13:55:09.9117913Z 
2026-10-21T13:55:09.9117965Z 10. DISCLAIMER OF WARRANTIES
2026-10-21T13:55:09.9118051Z 
2026-10-21T13:55:09.9118322Z 10.1 YOU EXPRESSLY UNDERSTAND AND AGREE THAT YOUR USE OF THE SDK IS AT YOUR SOLE RISK AND THAT THE SDK IS PROVIDED "AS IS" AND "AS AVAILABLE" WITHOUT WARRANTY OF ANY KIND FROM GOOGLE.
2026-10-21T13:55:09.9118635Z 
2026-10-21T13:55:09.9119064Z 10.2 YOUR USE OF THE SDK AND ANY MATERIAL DOWNLOADED OR OTHERWISE OBTAINED THROUGH THE USE OF THE SDK IS AT YOUR OWN DISCRETION AND RISK AND YOU ARE SOLELY RESPONSIBLE FOR ANY DAMAGE TO YOUR COMPUTER SYSTEM OR OTHER DEVICE OR LOSS OF DATA THAT RESULTS FROM SUCH USE.
2026-10-21T13:55:09.9119532Z 
2026-10-21T13:55:09.9119937Z 10.3 GOOGLE FURTHER EXPRESSLY DISCLAIMS ALL WARRANTIES AND CONDITIONS OF ANY KIND, WHETHER EXPRESS OR IMPLIED, INCLUDING, BUT NOT LIMITED TO THE IMPLIED WARRANTIES AND CONDITIONS OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT.
2026-10-21T13:55:09.9120392Z 
2026-10-21T13:55:09.9120394Z 
2026-10-21T13:55:09.9120447Z 11. LIMITATION OF LIABILITY
2026-10-21T13:55:09.9120532Z 
2026-10-21T13:55:09.9121235Z 11.1 YOU EXPRESSLY UNDERSTAND AND AGREE THAT GOOGLE, ITS SUBSIDIARIES AND AFFILIATES, AND ITS LICENSORS SHALL NOT BE LIABLE TO YOU UNDER ANY THEORY OF LIABILITY FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, CONSEQUENTIAL OR EXEMPLARY DAMAGES THAT MAY BE INCURRED BY YOU, INCLUDING ANY LOSS OF DATA, WHETHER OR NOT GOOGLE OR ITS REPRESENTATIVES HAVE BEEN ADVISED OF OR SHOULD HAVE BEEN AWARE OF THE POSSIBILITY OF ANY SUCH LOSSES ARISING.
2026-10-21T13:55:09.9122070Z 
2026-10-21T13:55:09.9122073Z 
2026-10-21T13:55:09.9122119Z 12. Indemnification
2026-10-21T13:55:09.9122191Z 
2026-10-21T13:55:09.9123372Z 12.1 To the maximum extent permitted by law, you agree to defend, indemnify and hold harmless Google, its affiliates and their respective directors, officers, employees and agents from and against any and all claims, actions, suits or proceedings, as well as any and all losses, liabilities, damages, costs and expenses (including reasonable attorneys fees) arising out of or accruing from (a) your use of the SDK, (b) any application you develop on the SDK that infringes any copyright, trademark, trade secret, trade dress, patent or other intellectual property right of any person or defames any person or violates their rights of publicity or privacy, and (c) any non-compliance by you with the License Agreement.
2026-10-21T13:55:09.9124575Z 
2026-10-21T13:55:09.9124581Z 
2026-10-21T13:55:09.9124639Z 13. Changes to the License Agreement
2026-10-21T13:55:09.9124739Z 
2026-10-21T13:55:09.9125109Z 13.1 Google may make changes to the License Agreement as it distributes new versions of the SDK. When these changes are made, Google will make a new version of the License Agreement available on the website where the SDK is made available.
2026-10-21T13:55:09.9125529Z 
2026-10-21T13:55:09.9125531Z 
2026-10-21T13:55:09.9125580Z 14. General Legal Terms
2026-10-21T13:55:09.9125656Z 
2026-10-21T13:55:09.9126141Z 14.1 The License Agreement constitutes the whole legal agreement between you and Google and governs your use of the SDK (excluding any services which Google may provide to you under a separate written agreement), and completely replaces any prior agreements between you and Google in relation to the SDK.
2026-10-21T13:55:09.9126670Z 
2026-10-21T13:55:09.9127302Z 14.2 You agree that if Google does not exercise or enforce any legal right or remedy which is contained in the License Agreement (or which Google has the benefit of under any applicable law), this will not be taken to be a formal waiver of Google's rights and that those rights or remedies will still be available to Google.
2026-10-21T13:55:09.9127968Z 
2026-10-21T13:55:09.9128510Z 14.3 If any court of law, having the jurisdiction to decide on this matter, rules that any provision of the License Agreement is invalid, then that provision will be removed from the License Agreement without affecting the rest of the License Agreement. The remaining provisions of the License Agreement will continue to be valid and enforceable.
2026-10-21T13:55:09.9129100Z 
2026-10-21T13:55:09.9129807Z 14.4 You acknowledge and agree that each member of the group of companies of which Google is the parent shall be third party beneficiaries to the License Agreement and that such other companies shall be entitled to directly enforce, and rely upon, any provision of the License Agreement that confers a benefit on (or rights in favor of) them. Other than this, no other person or company shall be third party beneficiaries to the License Agreement.
2026-10-21T13:55:09.9130559Z 
2026-10-21T13:55:09.9130978Z 14.5 EXPORT RESTRICTIONS. THE SDK IS SUBJECT TO UNITED STATES EXPORT LAWS AND REGULATIONS. YOU MUST COMPLY WITH ALL DOMESTIC AND INTERNATIONAL EXPORT LAWS AND REGULATIONS THAT APPLY TO THE SDK. THESE LAWS INCLUDE RESTRICTIONS ON DESTINATIONS, END USERS AND END USE.
2026-10-21T13:55:09.9131444Z 
2026-10-21T13:55:09.9131986Z 14.6 The rights granted in the License Agreement may not be assigned or transferred by either you or Google without the prior written approval of the other party. Neither you nor Google shall be permitted to delegate their responsibilities or obligations under the License Agreement without the prior written approval of the other party.
2026-10-21T13:55:09.9132576Z 
2026-10-21T13:55:09.9133480Z 14.7 The License Agreement, and your relationship with Google under the License Agreement, shall be governed by the laws of the State of California without regard to its conflict of laws provisions. You and Google agree to submit to the exclusive jurisdiction of the courts located within the county of Santa Clara, California to resolve any legal matter arising from the License Agreement. Notwithstanding this, you agree that Google shall still be allowed to apply for injunctive remedies (or an equivalent type of urgent legal relief) in any jurisdiction.
2026-10-21T13:55:09.9134503Z 
2026-10-21T13:55:09.9134506Z 
2026-10-21T13:55:09.9134551Z January 16, 2019
2026-10-21T13:55:09.9134672Z ---------------------------------------
2026-10-21T13:55:09.9134864Z Accept? (y/N): 
2026-10-21T13:55:09.9134999Z 4/7: License android-sdk-preview-license:
2026-10-21T13:55:09.9135156Z ---------------------------------------
2026-10-21T13:55:09.9135863Z To get started with the Android SDK Preview, you must agree to the following terms and conditions. As described below, please note that this is a preview version of the Android SDK, subject to change, that you use at your own risk. The Android SDK Preview is not a stable release, and may contain errors and defects that can result in serious damage to your computer systems, devices and data.
2026-10-21T13:55:09.9136532Z 
2026-10-21T13:55:09.9136643Z This is the Android SDK Preview License Agreement (the "License Agreement").
2026-10-21T13:55:09.9136901Z 
2026-10-21T13:55:09.9136973Z 1. Introduction
2026-10-21T13:55:09.9137062Z 
2026-10-21T13:55:09.9137921Z 1.1 The Android SDK Preview (referred to in the License Agreement as the “Preview” and specifically including the Android system files, packaged APIs, and Preview library files, if and when they are made available) is licensed to you subject to the terms of the License Agreement. The License Agreement forms a legally binding contract between you and Google in relation to your use of the Preview.
2026-10-21T13:55:09.9138596Z 
2026-10-21T13:55:09.9138932Z 1.2 "Android" means the Android software stack for devices, as made available under the Android Open Source Project, which is located at the following URL: http://source.android.com/, as updated from time to time.
2026-10-21T13:55:09.9139410Z 
2026-10-21T13:55:09.9139656Z 1.3 "Google" means Google Inc., a Delaware corporation with principal place of business at 1600 Amphitheatre Parkway, Mountain View, CA 94043, United States.
2026-10-21T13:55:09.9139943Z 
2026-10-21T13:55:09.9140004Z 2. Accepting the License Agreement
2026-10-21T13:55:09.9140099Z 
2026-10-21T13:55:09.9140323Z 2.1 In order to use the Preview, you must first agree to the License Agreement. You may not use the Preview if you do not accept the License Agreement.
2026-10-21T13:55:09.9140592Z 
2026-10-21T13:55:09.9140745Z 2.2 By clicking to accept and/or using the Preview, you hereby agree to the terms of the License Agreement.
2026-10-21T13:55:09.9140948Z 
2026-10-21T13:55:09.9141357Z 2.3 You may not use the Preview and may not accept the License Agreement if you are a person barred from receiving the Preview under the laws of the United States or other countries including the country in which you are resident or from which you use the Preview.
2026-10-21T13:55:09.9141817Z 
2026-10-21T13:55:09.9142510Z 2.4 If you will use the Preview internally within your company or organization you agree to be bound by the License Agreement on behalf of your employer or other entity, and you represent and warrant that you have full legal authority to bind your employer or such entity to the License Agreement. If you do not have the requisite authority, you may not accept the License Agreement or use the Preview on behalf of your employer or other entity.
2026-10-21T13:55:09.9143250Z 
2026-10-21T13:55:09.9143303Z 3. Preview License from Google
2026-10-21T13:55:09.9143393Z 
2026-10-21T13:55:09.9143920Z 3.1 Subject to the terms of the License Agreement, Google grants you a royalty-free, non-assignable, non-exclusive, non-sublicensable, limited, revocable license to use the Preview, personally or internally within your company or organization, solely to develop applications to run on the Android platform.
2026-10-21T13:55:09.9144478Z 
2026-10-21T13:55:09.9145122Z 3.2 You agree that Google or third parties owns all legal right, title and interest in and to the Preview, including any Intellectual Property Rights that subsist in the Preview. "Intellectual Property Rights" means any and all rights under patent law, copyright law, trade secret law, trademark law, and any and all other proprietary rights. Google reserves all rights not expressly granted to you.
2026-10-21T13:55:09.9145886Z 
2026-10-21T13:55:09.9146975Z 3.3 You may not use the Preview for any purpose not expressly permitted by the License Agreement. Except to the extent required by applicable third party licenses, you may not: (a) copy (except for backup purposes), modify, adapt, redistribute, decompile, reverse engineer, disassemble, or create derivative works of the Preview or any part of the Preview; or (b) load any part of the Preview onto a mobile handset or any other hardware device except a personal computer, combine any part of the Preview with other software, or distribute any software or device incorporating a part of the Preview.
2026-10-21T13:55:09.9148729Z 
2026-10-21T13:55:09.9149221Z 3.4 You agree that you will not take any actions that may cause or result in the fragmentation of Android, including but not limited to distributing, participating in the creation of, or promoting in any way a software development kit derived from the Preview.
2026-10-21T13:55:09.9149672Z 
2026-10-21T13:55:09.9150342Z 3.5 Use, reproduction and distribution of components of the Preview licensed under an open source software license are governed solely by the terms of that open source software license and not the License Agreement. You agree to remain a licensee in good standing in regard to such open source software licenses under all the rights granted and to refrain from any actions that may terminate, suspend, or breach such rights.
2026-10-21T13:55:09.9151059Z 
2026-10-21T13:55:09.9151766Z 3.6 You agree that the form and nature of the Preview that Google provides may change without prior notice to you and that future versions of the Preview may be incompatible with applications developed on previous versions of the Preview. You agree that Google may stop (permanently or temporarily) providing the Preview (or any features within the Preview) to you or to users generally at Google's sole discretion, without prior notice to you.
2026-10-21T13:55:09.9152600Z 
2026-10-21T13:55:09.9152867Z 3.7 Nothing in the License Agreement gives you a right to use any of Google's trade names, trademarks, service marks, logos, domain names, or other distinctive brand features.
2026-10-21T13:55:09.9153181Z 
2026-10-21T13:55:09.9153471Z 3.8 You agree that you will not remove, obscure, or alter any proprietary rights notices (including copyright and trademark notices) that may be affixed to or contained within the Preview.
2026-10-21T13:55:09.9153811Z 
2026-10-21T13:55:09.9153864Z 4. Use of the Preview by You
2026-10-21T13:55:09.9153951Z 
2026-10-21T13:55:09.9154440Z 4.1 Google agrees that nothing in the License Agreement gives Google any right, title or interest from you (or your licensors) under the License Agreement in or to any software applications that you develop using the Preview, including any intellectual property rights that subsist in those applications.
2026-10-21T13:55:09.9154973Z 
2026-10-21T13:55:09.9155545Z 4.2 You agree to use the Preview and write applications only for purposes that are permitted by (a) the License Agreement, and (b) any applicable law, regulation or generally accepted practices or guidelines in the relevant jurisdictions (including any laws regarding the export of data or software to and from the United States or other relevant countries).
2026-10-21T13:55:09.9156161Z 
2026-10-21T13:55:09.9157499Z 4.3 You agree that if you use the Preview to develop applications, you will protect the privacy and legal rights of users. If users provide you with user names, passwords, or other login information or personal information, you must make the users aware that the information will be available to your application, and you must provide legally adequate privacy notice and protection for those users. If your application stores personal or sensitive information provided by users, it must do so securely. If users provide you with Google Account information, your application may only use that information to access the user's Google Account when, and for the limited purposes for which, each user has given you permission to do so.
2026-10-21T13:55:09.9158804Z 
2026-10-21T13:55:09.9159272Z 4.4 You agree that you will not engage in any activity with the Preview, including the development or distribution of an application, that interferes with, disrupts, damages, or accesses in an unauthorized manner the servers, networks, or other properties or services of Google or any third party.
2026-10-21T13:55:09.9159788Z 
2026-10-21T13:55:09.9160335Z 4.5 You agree that you are solely responsible for (and that Google has no responsibility to you or to any third party for) any data, content, or resources that you create, transmit or display through Android and/or applications for Android, and for the consequences of your actions (including any loss or damage which Google may suffer) by doing so.
2026-10-21T13:55:09.9160928Z 
2026-10-21T13:55:09.9161554Z 4.6 You agree that you are solely responsible for (and that Google has no responsibility to you or to any third party for) any breach of your obligations under the License Agreement, any applicable third party contract or Terms of Service, or any applicable law or regulation, and for the consequences (including any loss or damage which Google or any third party may suffer) of any such breach.
2026-10-21T13:55:09.9162217Z 
2026-10-21T13:55:09.9162984Z 4.7 The Preview is in development, and your testing and feedback are an important part of the development process. By using the Preview, you acknowledge that implementation of some features are still under development and that you should not rely on the Preview having the full functionality of a stable release. You agree not to publicly distribute or ship any application using this Preview as this Preview will no longer be supported after the official Android SDK is released.
2026-10-21T13:55:09.9163879Z 
2026-10-21T13:55:09.9163937Z 5. Your Developer Credentials
2026-10-21T13:55:09.9164027Z 
2026-10-21T13:55:09.9164479Z 5.1 You agree that you are responsible for maintaining the confidentiality of any developer credentials that may be issued to you by Google or which you may choose yourself and that you will be solely responsible for all applications that are developed under your developer credentials.
2026-10-21T13:55:09.9164979Z 
2026-10-21T13:55:09.9165034Z 6. Privacy and Information
2026-10-21T13:55:09.9165119Z 
2026-10-21T13:55:09.9165907Z 6.1 In order to continually innovate and improve the Preview, Google may collect certain usage statistics from the software including but not limited to a unique identifier, associated IP address, version number of the software, and information on which tools and/or services in the Preview are being used and how they are being used. Before any of this information is collected, the Preview will notify you and seek your consent. If you withhold consent, the information will not be collected.
2026-10-21T13:55:09.9166742Z 
2026-10-21T13:55:09.9167188Z 6.2 The data collected is examined in the aggregate to improve the Preview and is maintained in accordance with Google's Privacy Policy located at http://www.google.com/policies/privacy/.
2026-10-21T13:55:09.9167526Z 
2026-10-21T13:55:09.9167583Z 7. Third Party Applications
2026-10-21T13:55:09.9167671Z 
2026-10-21T13:55:09.9168634Z 7.1 If you use the Preview to run applications developed by a third party or that access data, content or resources provided by a third party, you agree that Google is not responsible for those applications, data, content, or resources. You understand that all data, content or resources which you may access through such third party applications are the sole responsibility of the person from which they originated and that Google is not liable for any loss or damage that you may experience as a result of the use or access of any of those third party applications, data, content, or resources.
2026-10-21T13:55:09.9169723Z 
2026-10-21T13:55:09.9170496Z 7.2 You should be aware the data, content, and resources presented to you through such a third party application may be protected by intellectual property rights which are owned by the providers (or by other persons or companies on their behalf). You may not modify, rent, lease, loan, sell, distribute or create derivative works based on these data, content, or resources (either in whole or in part) unless you have been specifically given permission to do so by the relevant owners.
2026-10-21T13:55:09.9171314Z 
2026-10-21T13:55:09.9171573Z 7.3 You acknowledge that your use of such third party applications, data, content, or resources may be subject to separate terms between you and the relevant third party.
2026-10-21T13:55:09.9171880Z 
2026-10-21T13:55:09.9171928Z 8. Using Google APIs
2026-10-21T13:55:09.9172005Z 
2026-10-21T13:55:09.9172053Z 8.1 Google APIs
2026-10-21T13:55:09.9172123Z 
2026-10-21T13:55:09.9172913Z 8.1.1 If you use any API to retrieve data from Google, you acknowledge that the data may be protected by intellectual property rights which are owned by Google or those parties that provide the data (or by other persons or companies on their behalf). Your use of any such API may be subject to additional Terms of Service. You may not modify, rent, lease, loan, sell, distribute or create derivative works based on this data (either in whole or in part) unless allowed by the relevant Terms of Service.
2026-10-21T13:55:09.9173766Z 
2026-10-21T13:55:09.9174155Z 8.1.2 If you use any API to retrieve a user's data from Google, you acknowledge and agree that you shall retrieve data only with the user's explicit consent and only when, and for the limited purposes for which, the user has given you permission to do so.
2026-10-21T13:55:09.9174677Z 
2026-10-21T13:55:09.9174734Z 9. Terminating the License Agreement
2026-10-21T13:55:09.9174836Z 
2026-10-21T13:55:09.9175002Z 9.1 the License Agreement will continue to apply until terminated by either you or Google as set out below.
2026-10-21T13:55:09.9175208Z 
2026-10-21T13:55:09.9175418Z 9.2 If you want to terminate the License Agreement, you may do so by ceasing your use of the Preview and any relevant developer credentials.
2026-10-21T13:55:09.9175666Z 
2026-10-21T13:55:09.9175822Z 9.3 Google may at any time, terminate the License Agreement, with or without cause, upon notice to you.
2026-10-21T13:55:09.9176019Z 
2026-10-21T13:55:09.9176572Z 9.4 The License Agreement will automatically terminate without notice or other action upon the earlier of: (A) when Google ceases to provide the Preview or certain parts of the Preview to users in the country in which you are resident or from which you use the service; and (B) Google issues a final release version of the Android SDK.
2026-10-21T13:55:09.9177303Z 
2026-10-21T13:55:09.9177680Z 9.5 When the License Agreement is terminated, the license granted to you in the License Agreement will terminate, you will immediately cease all use of the Preview, and the provisions of paragraphs 10, 11, 12 and 14 shall survive indefinitely.
2026-10-21T13:55:09.9178107Z 
2026-10-21T13:55:09.9178153Z 10. DISCLAIMERS
2026-10-21T13:55:09.9178219Z 
2026-10-21T13:55:09.9178507Z 10.1 YOU EXPRESSLY UNDERSTAND AND AGREE THAT YOUR USE OF THE PREVIEW IS AT YOUR SOLE RISK AND THAT THE PREVIEW IS PROVIDED "AS IS" AND "AS AVAILABLE" WITHOUT WARRANTY OF ANY KIND FROM GOOGLE.
2026-10-21T13:55:09.9178838Z 
2026-10-21T13:55:09.9179731Z 10.2 YOUR USE OF THE PREVIEW AND ANY MATERIAL DOWNLOADED OR OTHERWISE OBTAINED THROUGH THE USE OF THE PREVIEW IS AT YOUR OWN DISCRETION AND RISK AND YOU ARE SOLELY RESPONSIBLE FOR ANY DAMAGE TO YOUR COMPUTER SYSTEM OR OTHER DEVICE OR LOSS OF DATA THAT RESULTS FROM SUCH USE. WITHOUT LIMITING THE FOREGOING, YOU UNDERSTAND THAT THE PREVIEW IS NOT A STABLE RELEASE AND MAY CONTAIN ERRORS, DEFECTS AND SECURITY VULNERABILITIES THAT CAN RESULT IN SIGNIFICANT DAMAGE, INCLUDING THE COMPLETE, IRRECOVERABLE LOSS OF USE OF YOUR COMPUTER SYSTEM OR OTHER DEVICE.
2026-10-21T13:55:09.9180745Z 
2026-10-21T13:55:09.9181155Z 10.3 GOOGLE FURTHER EXPRESSLY DISCLAIMS ALL WARRANTIES AND CONDITIONS OF ANY KIND, WHETHER EXPRESS OR IMPLIED, INCLUDING, BUT NOT LIMITED TO THE IMPLIED WARRANTIES AND CONDITIONS OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT.
2026-10-21T13:55:09.9181606Z 
2026-10-21T13:55:09.9181659Z 11. LIMITATION OF LIABILITY
2026-10-21T13:55:09.9181745Z 
2026-10-21T13:55:09.9182442Z 11.1 YOU EXPRESSLY UNDERSTAND AND AGREE THAT GOOGLE, ITS SUBSIDIARIES AND AFFILIATES, AND ITS LICENSORS SHALL NOT BE LIABLE TO YOU UNDER ANY THEORY OF LIABILITY FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, CONSEQUENTIAL OR EXEMPLARY DAMAGES THAT MAY BE INCURRED BY YOU, INCLUDING ANY LOSS OF DATA, WHETHER OR NOT GOOGLE OR ITS REPRESENTATIVES HAVE BEEN ADVISED OF OR SHOULD HAVE BEEN AWARE OF THE POSSIBILITY OF ANY SUCH LOSSES ARISING.
2026-10-21T13:55:09.9183186Z 
2026-10-21T13:55:09.9183237Z 12. Indemnification
2026-10-21T13:55:09.9183308Z 
2026-10-21T13:55:09.9184710Z 12.1 To the maximum extent permitted by law, you agree to defend, indemnify and hold harmless Google, its affiliates and their respective directors, officers, employees and agents from and against any and all claims, actions, suits or proceedings, as well as any and all losses, liabilities, damages, costs and expenses (including reasonable attorneys’ fees) arising out of or accruing from (a) your use of the Preview, (b) any application you develop on the Preview that infringes any Intellectual Property Rights of any person or defames any person or violates their rights of publicity or privacy, and (c) any non-compliance by you of the License Agreement.
2026-10-21T13:55:09.9185821Z 
2026-10-21T13:55:09.9185876Z 13. Changes to the License Agreement
2026-10-21T13:55:09.9185973Z 
2026-10-21T13:55:09.9186445Z 13.1 Google may make changes to the License Agreement as it distributes new versions of the Preview. When these changes are made, Google will make a new version of the License Agreement available on the website where the Preview is made available.
2026-10-21T13:55:09.9186943Z 
2026-10-21T13:55:09.9186993Z 14. General Legal Terms
2026-10-21T13:55:09.9187069Z 
2026-10-21T13:55:09.9187571Z 14.1 the License Agreement constitutes the whole legal agreement between you and Google and governs your use of the Preview (excluding any services which Google may provide to you under a separate written agreement), and completely replaces any prior agreements between you and Google in relation to the Preview.
2026-10-21T13:55:09.9188116Z 
2026-10-21T13:55:09.9188621Z 14.2 You agree that if Google does not exercise or enforce any legal right or remedy which is contained in the License Agreement (or which Google has the benefit of under any applicable law), this will not be taken to be a formal waiver of Google's rights and that those rights or remedies will still be available to Google.
2026-10-21T13:55:09.9189193Z 
2026-10-21T13:55:09.9189744Z 14.3 If any court of law, having the jurisdiction to decide on this matter, rules that any provision of the License Agreement is invalid, then that provision will be removed from the License Agreement without affecting the rest of the License Agreement. The remaining provisions of the License Agreement will continue to be valid and enforceable.
2026-10-21T13:55:09.9190336Z 
2026-10-21T13:55:09.9191053Z 14.4 You acknowledge and agree that each member of the group of companies of which Google is the parent shall be third party beneficiaries to the License Agreement and that such other companies shall be entitled to directly enforce, and rely upon, any provision of the License Agreement that confers a benefit on (or rights in favor of) them. Other than this, no other person or company shall be third party beneficiaries to the License Agreement.
2026-10-21T13:55:09.9191833Z 
2026-10-21T13:55:09.9192272Z 14.5 EXPORT RESTRICTIONS. THE PREVIEW IS SUBJECT TO UNITED STATES EXPORT LAWS AND REGULATIONS. YOU MUST COMPLY WITH ALL DOMESTIC AND INTERNATIONAL EXPORT LAWS AND REGULATIONS THAT APPLY TO THE PREVIEW. THESE LAWS INCLUDE RESTRICTIONS ON DESTINATIONS, END USERS AND END USE.
2026-10-21T13:55:09.9192909Z 
2026-10-21T13:55:09.9193504Z 14.6 The License Agreement may not be assigned or transferred by you without the prior written approval of Google, and any attempted assignment without such approval will be void. You shall not delegate your responsibilities or obligations under the License Agreement without the prior written approval of Google.
2026-10-21T13:55:09.9194054Z 
2026-10-21T13:55:09.9194964Z 14.7 The License Agreement, and your relationship with Google under the License Agreement, shall be governed by the laws of the State of California without regard to its conflict of laws provisions. You and Google agree to submit to the exclusive jurisdiction of the courts located within the county of Santa Clara, California to resolve any legal matter arising from the License Agreement. Notwithstanding this, you agree that Google shall still be allowed to apply for injunctive remedies (or an equivalent type of urgent legal relief) in any jurisdiction.
2026-10-21T13:55:09.9195909Z 
2026-10-21T13:55:09.9195956Z June 2014.
2026-10-21T13:55:09.9196074Z ---------------------------------------
2026-10-21T13:55:09.9196220Z Accept? (y/N): 
2026-10-21T13:55:09.9196346Z 5/7: License google-gdk-license:
2026-10-21T13:55:09.9196491Z ---------------------------------------
2026-10-21T13:55:09.9196679Z This is a Developer Preview of the GDK that is subject to change.
2026-10-21T13:55:09.9196884Z 
2026-10-21T13:55:09.9196931Z Terms and Conditions
2026-10-21T13:55:09.9197071Z 
2026-10-21T13:55:09.9197149Z This is the Glass Development Kit License Agreement.
2026-10-21T13:55:09.9197273Z 
2026-10-21T13:55:09.9197321Z 1. Introduction
2026-10-21T13:55:09.9197385Z 
2026-10-21T13:55:09.9198181Z 1.1 The Glass Development Kit (referred to in this License Agreement as the "GDK" and specifically including the Android system files, packaged APIs, and GDK library files, if and when they are made available) is licensed to you subject to the terms of this License Agreement. This License Agreement forms a legally binding contract between you and Google in relation to your use of the GDK.
2026-10-21T13:55:09.9198937Z 
2026-10-21T13:55:09.9199057Z 1.2 "Glass" means Glass devices and the Glass software stack for use on Glass devices.
2026-10-21T13:55:09.9199232Z 
2026-10-21T13:55:09.9199235Z 
2026-10-21T13:55:09.9199572Z 1.3 "Android" means the Android software stack for devices, as made available under the Android Open Source Project, which is located at the following URL: http://source.android.com/, as updated from time to time.
2026-10-21T13:55:09.9199952Z 
2026-10-21T13:55:09.9200199Z 1.4 "Google" means Google Inc., a Delaware corporation with principal place of business at 1600 Amphitheatre Parkway, Mountain View, CA 94043, United States.
2026-10-21T13:55:09.9200489Z 
2026-10-21T13:55:09.9200550Z 2. Accepting this License Agreement
2026-10-21T13:55:09.9200647Z 
2026-10-21T13:55:09.9200860Z 2.1 In order to use the GDK, you must first agree to this License Agreement. You may not use the GDK if you do not accept this License Agreement.
2026-10-21T13:55:09.9201119Z 
2026-10-21T13:55:09.9201236Z 2.2 By clicking to accept, you hereby agree to the terms of this License Agreement.
2026-10-21T13:55:09.9201406Z 
2026-10-21T13:55:09.9201792Z 2.3 You may not use the GDK and may not accept the License Agreement if you are a person barred from receiving the GDK under the laws of the United States or other countries including the country in which you are resident or from which you use the GDK.
2026-10-21T13:55:09.9202226Z 
2026-10-21T13:55:09.9202804Z 2.4 If you are agreeing to be bound by this License Agreement on behalf of your employer or other entity, you represent and warrant that you have full legal authority to bind your employer or such entity to this License Agreement. If you do not have the requisite authority, you may not accept the License Agreement or use the GDK on behalf of your employer or other entity.
2026-10-21T13:55:09.9203506Z 
2026-10-21T13:55:09.9203558Z 3. GDK License from Google
2026-10-21T13:55:09.9203642Z 
2026-10-21T13:55:09.9204027Z 3.1 Subject to the terms of this License Agreement, Google grants you a limited, worldwide, royalty-free, non-assignable and non-exclusive license to use the GDK solely to develop applications to run on the Glass platform for Glass devices.
2026-10-21T13:55:09.9204463Z 
2026-10-21T13:55:09.9205086Z 3.2 You agree that Google or third parties own all legal right, title and interest in and to the GDK, including any Intellectual Property Rights that subsist in the GDK. "Intellectual Property Rights" means any and all rights under patent law, copyright law, trade secret law, trademark law, and any and all other proprietary rights. Google reserves all rights not expressly granted to you.
2026-10-21T13:55:09.9205754Z 
2026-10-21T13:55:09.9206742Z 3.3 You may not use the GDK for any purpose not expressly permitted by this License Agreement. Except to the extent required by applicable third party licenses, you may not: (a) copy (except for backup purposes), modify, adapt, redistribute, decompile, reverse engineer, disassemble, or create derivative works of the GDK or any part of the GDK; or (b) load any part of the GDK onto a mobile handset or wearable computing device or any other hardware device except a Glass device personal computer, combine any part of the GDK with other software, or distribute any software or device incorporating a part of the GDK.
2026-10-21T13:55:09.9207934Z 
2026-10-21T13:55:09.9208328Z 3.4 You agree that you will not take any actions that may cause or result in the fragmentation of Glass, including but not limited to distributing, participating in the creation of, or promoting in any way a software development kit derived from the GDK.
2026-10-21T13:55:09.9208767Z 
2026-10-21T13:55:09.9209179Z 3.5 Use, reproduction and distribution of components of the GDK licensed under an open source software license are governed solely by the terms of that open source software license and not this License Agreement.
2026-10-21T13:55:09.9209556Z 
2026-10-21T13:55:09.9210227Z 3.6 You agree that the form and nature of the GDK that Google provides may change without prior notice to you and that future versions of the GDK may be incompatible with applications developed on previous versions of the GDK. You agree that Google may stop (permanently or temporarily) providing the GDK (or any features within the GDK) to you or to users generally at Google's sole discretion, without prior notice to you.
2026-10-21T13:55:09.9210935Z 
2026-10-21T13:55:09.9211201Z 3.7 Nothing in this License Agreement gives you a right to use any of Google's trade names, trademarks, service marks, logos, domain names, or other distinctive brand features.
2026-10-21T13:55:09.9211517Z 
2026-10-21T13:55:09.9211805Z 3.8 You agree that you will not remove, obscure, or alter any proprietary rights notices (including copyright and trademark notices) that may be affixed to or contained within the GDK.
2026-10-21T13:55:09.9212143Z 
2026-10-21T13:55:09.9212145Z 
2026-10-21T13:55:09.9212730Z 3.9 Your use of any Android system files, packaged APIs, or other components of the GDK which are part of the Android Software Development Kit is subject to the terms of the Android Software Development Kit License Agreement located at http://developer.android.com/sdk/terms.html. These terms are hereby incorporated by reference into this License Agreement.
2026-10-21T13:55:09.9213353Z 
2026-10-21T13:55:09.9213402Z 4. Use of the GDK by You
2026-10-21T13:55:09.9213483Z 
2026-10-21T13:55:09.9213903Z 4.1 Google agrees that it obtains no right, title or interest from you (or your licensors) under this License Agreement in or to any software applications that you develop using the GDK, including any intellectual property rights that subsist in those applications.
2026-10-21T13:55:09.9214364Z 
2026-10-21T13:55:09.9215222Z 4.2 You agree to use the GDK and write applications only for purposes that are permitted by (a) this License Agreement, (b) the Glass Platform Developer Policies (located at https://developers.google.com/glass/policies, and hereby incorporated into this License Agreement by reference), and (c) any applicable law, regulation or generally accepted practices or guidelines in the relevant jurisdictions (including any laws regarding the export of data or software to and from the United States or other relevant countries).
2026-10-21T13:55:09.9216482Z 
2026-10-21T13:55:09.9218336Z 4.3 You agree that if you use the GDK to develop applications for general public users, you will protect the privacy and legal rights of those users. If the users provide you with user names, passwords, or other login information or personal information, you must make the users aware that the information will be available to your application, and you must provide legally adequate privacy notice and protection for those users. If your application stores personal or sensitive information provided by users, it must do so securely. If the user provides your application with Google Account information, your application may only use that information to access the user's Google Account when, and for the limited purposes for which, the user has given you permission to do so.
2026-10-21T13:55:09.9220507Z 
2026-10-21T13:55:09.9221035Z 4.4 You agree that you will not engage in any activity with the GDK, including the development or distribution of an application, that interferes with, disrupts, damages, or accesses in an unauthorized manner the servers, networks, or other properties or services of any third party including, but not limited to, Google.
2026-10-21T13:55:09.9221589Z 
2026-10-21T13:55:09.9222131Z 4.5 You agree that you are solely responsible for (and that Google has no responsibility to you or to any third party for) any data, content, or resources that you create, transmit or display through Glass and/or applications for Glass, and for the consequences of your actions (including any loss or damage which Google may suffer) by doing so.
2026-10-21T13:55:09.9222817Z 
2026-10-21T13:55:09.9223465Z 4.6 You agree that you are solely responsible for (and that Google has no responsibility to you or to any third party for) any breach of your obligations under this License Agreement, any applicable third party contract or Terms of Service, or any applicable law or regulation, and for the consequences (including any loss or damage which Google or any third party may suffer) of any such breach.
2026-10-21T13:55:09.9224134Z 
2026-10-21T13:55:09.9224136Z 
2026-10-21T13:55:09.9224732Z 4.7 The GDK is in development, and your testing and feedback are an important part of the development process. By using the GDK, you acknowledge that implementation of some features are still under development and that you should not rely on the GDK, Glass devices, Glass system software, Google Mirror API, or Glass services having the full functionality of a stable release.
2026-10-21T13:55:09.9225387Z 
2026-10-21T13:55:09.9225448Z 5. Your Developer Credentials
2026-10-21T13:55:09.9225535Z 
2026-10-21T13:55:09.9225992Z 5.1 You agree that you are responsible for maintaining the confidentiality of any developer credentials that may be issued to you by Google or which you may choose yourself and that you will be solely responsible for all applications that are developed under your developer credentials.
2026-10-21T13:55:09.9226493Z 
2026-10-21T13:55:09.9226547Z 6. Privacy and Information
2026-10-21T13:55:09.9226633Z 
2026-10-21T13:55:09.9226636Z 
2026-10-21T13:55:09.9227531Z 6.1 In order to continually innovate and improve the GDK, Google may collect certain usage statistics from the software including but not limited to a unique identifier, associated IP address, version number of the software, and information on which tools and/or services in the GDK are being used and how they are being used. Before any of this information is collected, the GDK will notify you and seek your consent. If you withhold consent, the information will not be collected.
2026-10-21T13:55:09.9228433Z 
2026-10-21T13:55:09.9228633Z 6.2 The data collected is examined in the aggregate to improve the GDK and is maintained in accordance with Google's Privacy Policy.
2026-10-21T13:55:09.9228877Z 
2026-10-21T13:55:09.9228935Z 7. Third Party Applications
2026-10-21T13:55:09.9229024Z 
2026-10-21T13:55:09.9229981Z 7.1 If you use the GDK to run applications developed by a third party or that access data, content or resources provided by a third party, you agree that Google is not responsible for those applications, data, content, or resources. You understand that all data, content or resources which you may access through such third party applications are the sole responsibility of the person from which they originated and that Google is not liable for any loss or damage that you may experience as a result of the use or access of any of those third party applications, data, content, or resources.
2026-10-21T13:55:09.9230979Z 
2026-10-21T13:55:09.9231765Z 7.2 You should be aware the data, content, and resources presented to you through such a third party application may be protected by intellectual property rights which are owned by the providers (or by other persons or companies on their behalf). You may not modify, rent, lease, loan, sell, distribute or create derivative works based on these data, content, or resources (either in whole or in part) unless you have been specifically given permission to do so by the relevant owners.
2026-10-21T13:55:09.9232582Z 
2026-10-21T13:55:09.9233008Z 7.3 You acknowledge that your use of such third party applications, data, content, or resources may be subject to separate terms between you and the relevant third party. In that case, this License Agreement does not affect your legal relationship with these third parties.
2026-10-21T13:55:09.9233486Z 
2026-10-21T13:55:09.9233533Z 8. Using Google APIs
2026-10-21T13:55:09.9233611Z 
2026-10-21T13:55:09.9233740Z 8.1 Google APIs
2026-10-21T13:55:09.9233806Z 
2026-10-21T13:55:09.9234595Z 8.1.1 If you use any API to retrieve data from Google, you acknowledge that the data may be protected by intellectual property rights which are owned by Google or those parties that provide the data (or by other persons or companies on their behalf). Your use of any such API may be subject to additional Terms of Service. You may not modify, rent, lease, loan, sell, distribute or create derivative works based on this data (either in whole or in part) unless allowed by the relevant Terms of Service.
2026-10-21T13:55:09.9235437Z 
2026-10-21T13:55:09.9235826Z 8.1.2 If you use any API to retrieve a user's data from Google, you acknowledge and agree that you shall retrieve data only with the user's explicit consent and only when, and for the limited purposes for which, the user has given you permission to do so.
2026-10-21T13:55:09.9236261Z 
2026-10-21T13:55:09.9236321Z 9. Terminating this License Agreement
2026-10-21T13:55:09.9236423Z 
2026-10-21T13:55:09.9236589Z 9.1 This License Agreement will continue to apply until terminated by either you or Google as set out below.
2026-10-21T13:55:09.9236862Z 
2026-10-21T13:55:09.9237062Z 9.2 If you want to terminate this License Agreement, you may do so by ceasing your use of the GDK and any relevant developer credentials.
2026-10-21T13:55:09.9237309Z 
2026-10-21T13:55:09.9238330Z 9.3 Google may at any time, terminate this License Agreement with you if: (A) you have breached any provision of this License Agreement; or (B) Google is required to do so by law; or (C) the partner with whom Google offered certain parts of GDK (such as APIs) to you has terminated its relationship with Google or ceased to offer certain parts of the GDK to you; or (D) Google decides to no longer provide the GDK or certain parts of the GDK to users in the country in which you are resident or from which you use the service, or the provision of the GDK or certain GDK services to you by Google is, in Google's sole discretion, no longer commercially viable.
2026-10-21T13:55:09.9239393Z 
2026-10-21T13:55:09.9240107Z 9.4 When this License Agreement comes to an end, all of the legal rights, obligations and liabilities that you and Google have benefited from, been subject to (or which have accrued over time whilst this License Agreement has been in force) or which are expressed to continue indefinitely, shall be unaffected by this cessation, and the provisions of paragraph 14.7 shall continue to apply to such rights, obligations and liabilities indefinitely.
2026-10-21T13:55:09.9240941Z 
2026-10-21T13:55:09.9240994Z 10. DISCLAIMER OF WARRANTIES
2026-10-21T13:55:09.9241083Z 
2026-10-21T13:55:09.9241349Z 10.1 YOU EXPRESSLY UNDERSTAND AND AGREE THAT YOUR USE OF THE GDK IS AT YOUR SOLE RISK AND THAT THE GDK IS PROVIDED "AS IS" AND "AS AVAILABLE" WITHOUT WARRANTY OF ANY KIND FROM GOOGLE.
2026-10-21T13:55:09.9241662Z 
2026-10-21T13:55:09.9242070Z 10.2 YOUR USE OF THE GDK AND ANY MATERIAL DOWNLOADED OR OTHERWISE OBTAINED THROUGH THE USE OF THE GDK IS AT YOUR OWN DISCRETION AND RISK AND YOU ARE SOLELY RESPONSIBLE FOR ANY DAMAGE TO YOUR COMPUTER SYSTEM OR OTHER DEVICE OR LOSS OF DATA THAT RESULTS FROM SUCH USE.
2026-10-21T13:55:09.9242530Z 
2026-10-21T13:55:09.9242944Z 10.3 GOOGLE FURTHER EXPRESSLY DISCLAIMS ALL WARRANTIES AND CONDITIONS OF ANY KIND, WHETHER EXPRESS OR IMPLIED, INCLUDING, BUT NOT LIMITED TO THE IMPLIED WARRANTIES AND CONDITIONS OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT.
2026-10-21T13:55:09.9243404Z 
2026-10-21T13:55:09.9243477Z 11. LIMITATION OF LIABILITY
2026-10-21T13:55:09.9243559Z 
2026-10-21T13:55:09.9244261Z 11.1 YOU EXPRESSLY UNDERSTAND AND AGREE THAT GOOGLE, ITS SUBSIDIARIES AND AFFILIATES, AND ITS LICENSORS SHALL NOT BE LIABLE TO YOU UNDER ANY THEORY OF LIABILITY FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, CONSEQUENTIAL OR EXEMPLARY DAMAGES THAT MAY BE INCURRED BY YOU, INCLUDING ANY LOSS OF DATA, WHETHER OR NOT GOOGLE OR ITS REPRESENTATIVES HAVE BEEN ADVISED OF OR SHOULD HAVE BEEN AWARE OF THE POSSIBILITY OF ANY SUCH LOSSES ARISING.
2026-10-21T13:55:09.9245193Z 
2026-10-21T13:55:09.9245270Z 12. Indemnification
2026-10-21T13:55:09.9245369Z 
2026-10-21T13:55:09.9246554Z 12.1 To the maximum extent permitted by law, you agree to defend, indemnify and hold harmless Google, its affiliates and their respective directors, officers, employees and agents from and against any and all claims, actions, suits or proceedings, as well as any and all losses, liabilities, damages, costs and expenses (including reasonable attorneys fees) arising out of or accruing from (a) your use of the GDK, (b) any application you develop on the GDK that infringes any copyright, trademark, trade secret, trade dress, patent or other intellectual property right of any person or defames any person or violates their rights of publicity or privacy, and (c) any non-compliance by you with this License Agreement.
2026-10-21T13:55:09.9247902Z 
2026-10-21T13:55:09.9247956Z 13. Changes to the License Agreement
2026-10-21T13:55:09.9248057Z 
2026-10-21T13:55:09.9248423Z 13.1 Google may make changes to the License Agreement as it distributes new versions of the GDK. When these changes are made, Google will make a new version of the License Agreement available on the website where the GDK is made available.
2026-10-21T13:55:09.9248842Z 
2026-10-21T13:55:09.9248894Z 14. General Legal Terms
2026-10-21T13:55:09.9248968Z 
2026-10-21T13:55:09.9249460Z 14.1 This License Agreement constitutes the whole legal agreement between you and Google and governs your use of the GDK (excluding any services which Google may provide to you under a separate written agreement), and completely replaces any prior agreements between you and Google in relation to the GDK.
2026-10-21T13:55:09.9249994Z 
2026-10-21T13:55:09.9250498Z 14.2 You agree that if Google does not exercise or enforce any legal right or remedy which is contained in this License Agreement (or which Google has the benefit of under any applicable law), this will not be taken to be a formal waiver of Google's rights and that those rights or remedies will still be available to Google.
2026-10-21T13:55:09.9251139Z 
2026-10-21T13:55:09.9251688Z 14.3 If any court of law, having the jurisdiction to decide on this matter, rules that any provision of this License Agreement is invalid, then that provision will be removed from this License Agreement without affecting the rest of this License Agreement. The remaining provisions of this License Agreement will continue to be valid and enforceable.
2026-10-21T13:55:09.9252286Z 
2026-10-21T13:55:09.9253001Z 14.4 You acknowledge and agree that each member of the group of companies of which Google is the parent shall be third party beneficiaries to this License Agreement and that such other companies shall be entitled to directly enforce, and rely upon, any provision of this License Agreement that confers a benefit on (or rights in favor of) them. Other than this, no other person or company shall be third party beneficiaries to this License Agreement.
2026-10-21T13:55:09.9253758Z 
2026-10-21T13:55:09.9254173Z 14.5 EXPORT RESTRICTIONS. THE GDK IS SUBJECT TO UNITED STATES EXPORT LAWS AND REGULATIONS. YOU MUST COMPLY WITH ALL DOMESTIC AND INTERNATIONAL EXPORT LAWS AND REGULATIONS THAT APPLY TO THE GDK. THESE LAWS INCLUDE RESTRICTIONS ON DESTINATIONS, END USERS AND END USE.
2026-10-21T13:55:09.9254640Z 
2026-10-21T13:55:09.9255191Z 14.6 The rights granted in this License Agreement may not be assigned or transferred by either you or Google without the prior written approval of the other party. Neither you nor Google shall be permitted to delegate their responsibilities or obligations under this License Agreement without the prior written approval of the other party.
2026-10-21T13:55:09.9255782Z 
2026-10-21T13:55:09.9256696Z 14.7 This License Agreement, and your relationship with Google under this License Agreement, shall be governed by the laws of the State of California without regard to its conflict of laws provisions. You and Google agree to submit to the exclusive jurisdiction of the courts located within the county of Santa Clara, California to resolve any legal matter arising from this License Agreement. Notwithstanding this, you agree that Google shall still be allowed to apply for injunctive remedies (or an equivalent type of urgent legal relief) in any jurisdiction.
2026-10-21T13:55:09.9257875Z 
2026-10-21T13:55:09.9257921Z November 19, 2013
2026-10-21T13:55:09.9258045Z ---------------------------------------
2026-10-21T13:55:09.9258190Z Accept? (y/N): 
2026-10-21T13:55:09.9258320Z 6/7: License microxr-sysimage-license:
2026-10-21T13:55:09.9258475Z ---------------------------------------
2026-10-21T13:55:09.9258953Z To get started with the microXR Emulator System Image SDK, you must agree to the following terms and conditions. As described below, please note that this is a preview, emulated version of microXR, subject to change, that you use at your own risk.
2026-10-21T13:55:09.9259385Z 
2026-10-21T13:55:09.9259497Z This is the microXR Emulator System Image SDK License Agreement 1. Introduction
2026-10-21T13:55:09.9259668Z 
2026-10-21T13:55:09.9260365Z 1.1 The microXR Emulator System Image SDK (referred to in the License Agreement as the "SDK" and specifically including the Android system files,, packaged APIs, library files (if and when they are made available), and Google applications and APIs add-ons) is licensed to you subject to the terms of the License Agreement. The License Agreement forms a legally binding contract between you and Google in relation to your use of the SDK.
2026-10-21T13:55:09.9261106Z 
2026-10-21T13:55:09.9261449Z 1.2 "Android" means the Android software stack for devices, as made available under the Android Open Source Project, which is located at the following URL: https://source.android.com/, as updated from time to time.
2026-10-21T13:55:09.9261833Z 
2026-10-21T13:55:09.9262166Z 1.3 "Google" means Google LLC, organized under the laws of the State of Delaware, USA, and operating under the laws of the USA with principal place of business at 1600 Amphitheatre Parkway, Mountain View, CA 94043, USA.
2026-10-21T13:55:09.9262551Z 
2026-10-21T13:55:09.9262639Z 
2026-10-21T13:55:09.9262694Z 2. Accepting this License Agreement
2026-10-21T13:55:09.9262793Z 
2026-10-21T13:55:09.9263004Z 2.1 In order to use the SDK, you must first agree to the License Agreement. You may not use the SDK if you do not accept the License Agreement.
2026-10-21T13:55:09.9263258Z 
2026-10-21T13:55:09.9263410Z 2.2 By clicking to accept and/or using this SDK, you hereby agree to the terms of the License Agreement.
2026-10-21T13:55:09.9263604Z 
2026-10-21T13:55:09.9263994Z 2.3 You may not use the SDK and may not accept the License Agreement if you are a person barred from receiving the SDK under the laws of the United States or other countries, including the country in which you are resident or from which you use the SDK.
2026-10-21T13:55:09.9264432Z 
2026-10-21T13:55:09.9265047Z 2.4 If you are agreeing to be bound by the License Agreement on behalf of your employer or other entity, you represent and warrant that you have full legal authority to bind your employer or such entity to the License Agreement. If you do not have the requisite authority, you may not accept the License Agreement or use the SDK on behalf of your employer or other entity. 3. SDK License from Google
2026-10-21T13:55:09.9265708Z 
2026-10-21T13:55:09.9266101Z 3.1 Subject to the terms of the License Agreement, Google grants you a limited, worldwide, royalty-free, non-assignable, non-exclusive, and non-sublicensable license to use the SDK solely to develop applications for microXR and/or Android XR.
2026-10-21T13:55:09.9266537Z 
2026-10-21T13:55:09.9266948Z 3.2 You may not use this SDK to develop applications for other platforms or to develop another SDK. You are of course free to develop applications for other platforms provided that this SDK is not used for that purpose.
2026-10-21T13:55:09.9267337Z 
2026-10-21T13:55:09.9267960Z 3.3 You agree that Google or third parties own all legal right, title and interest in and to the SDK, including any Intellectual Property Rights that subsist in the SDK. "Intellectual Property Rights" means any and all rights under patent law, copyright law, trade secret law, trademark law, and any and all other proprietary rights. Google reserves all rights not expressly granted to you.
2026-10-21T13:55:09.9268720Z 
2026-10-21T13:55:09.9269641Z 3.4 You may not use the SDK for any purpose not expressly permitted by the License Agreement. Except to the extent required by applicable third party licenses, you may not (a) copy (except for backup purposes), modify, adapt, redistribute, decompile, reverse engineer, disassemble, or create derivative works of the SDK or any part of the SDK; or (b) load any part of the SDK onto a mobile handset or any other hardware device except a personal computer, combine any part of the SDK with other software, or distribute any software or device incorporating a part of the SDK.
2026-10-21T13:55:09.9270602Z 
2026-10-21T13:55:09.9271266Z 3.5 Use, reproduction and distribution of components of the SDK licensed under an open source software license are governed solely by the terms of that open source software license and not the License Agreement. You agree to remain a licensee in good standing in regard to such open source software licenses under all the rights granted and to refrain from any actions that may terminate, suspend, or breach such rights.
2026-10-21T13:55:09.9271982Z 
2026-10-21T13:55:09.9272651Z 3.6 You agree that the form and nature of the SDK that Google provides may change without prior notice to you and that future versions of the SDK may be incompatible with applications developed on previous versions of the SDK. You agree that Google may stop (permanently or temporarily) providing the SDK (or any features within the SDK) to you or to users generally at Google's sole discretion, without prior notice to you.
2026-10-21T13:55:09.9273361Z 
2026-10-21T13:55:09.9273625Z 3.7 Nothing in the License Agreement gives you a right to use any of Google's trade names, trademarks, service marks, logos, domain names, or other distinctive brand features.
2026-10-21T13:55:09.9274074Z 
2026-10-21T13:55:09.9274359Z 3.8 You agree that you will not remove, obscure, or alter any proprietary rights notices (including copyright and trademark notices) that may be affixed to or contained within the SDK.
2026-10-21T13:55:09.9274695Z 
2026-10-21T13:55:09.9274748Z 4. Use of the SDK by You
2026-10-21T13:55:09.9274829Z 
2026-10-21T13:55:09.9275252Z 4.1 Google agrees that it obtains no right, title or interest from you (or your licensors) under the License Agreement in or to any software applications that you develop using the SDK, including any intellectual property rights that subsist in those applications.
2026-10-21T13:55:09.9275709Z 
2026-10-21T13:55:09.9276394Z 4.2 You agree to use the SDK and write applications only for purposes that are permitted by (a) the License Agreement and (b) any applicable law, regulation or generally accepted practices or guidelines in the relevant jurisdictions (including any laws regarding the export of data or software to and from the United States or other relevant countries).
2026-10-21T13:55:09.9277491Z 
2026-10-21T13:55:09.9279500Z 4.3 You agree that if you use the SDK to develop applications for general public users, you will protect the privacy and legal rights of those users. If the users provide you with user names, passwords, or other login information or personal information, you must make the users aware that the information will be available to your application, and you must provide legally adequate privacy notice and protection for those users. If your application stores personal or sensitive information provided by users, it must do so securely. If the user provides your application with Google Account information, your application may only use that information to access the user's Google Account when, and for the limited purposes for which, the user has given you permission to do so.
2026-10-21T13:55:09.9281176Z 
2026-10-21T13:55:09.9281860Z 4.4 You agree that you will not engage in any activity with the SDK, including the development or distribution of an application, that interferes with, disrupts, damages, or accesses in an unauthorized manner the servers, networks, or other properties or services of any third party including, but not limited to, Google or any mobile communications carrier.
2026-10-21T13:55:09.9282478Z 
2026-10-21T13:55:09.9283024Z 4.5 You agree that you are solely responsible for (and that Google has no responsibility to you or to any third party for) any data, content, or resources that you create, transmit or display through Android and/or applications for Android, and for the consequences of your actions (including any loss or damage which Google may suffer) by doing so.
2026-10-21T13:55:09.9283617Z 
2026-10-21T13:55:09.9284244Z 4.6 You agree that you are solely responsible for (and that Google has no responsibility to you or to any third party for) any breach of your obligations under the License Agreement, any applicable third party contract or Terms of Service, or any applicable law or regulation, and for the consequences (including any loss or damage which Google or any third party may suffer) of any such breach.
2026-10-21T13:55:09.9284917Z 
2026-10-21T13:55:09.9285388Z 4.7 The SDK is in development, and your testing and feedback are an important part of the development process. By using the SDK, you acknowledge that implementation of some features are still under development and that you should not rely on the SDK having the full functionality of a stable release.
2026-10-21T13:55:09.9285908Z 
2026-10-21T13:55:09.9285966Z 5. Your Developer Credentials
2026-10-21T13:55:09.9286054Z 
2026-10-21T13:55:09.9286552Z 5.1 You agree that you are responsible for maintaining the confidentiality of any developer credentials that may be issued to you by Google or which you may choose yourself and that you will be solely responsible for all applications that are developed under your developer credentials. 6. Privacy and Information
2026-10-21T13:55:09.9287241Z 
2026-10-21T13:55:09.9288005Z 6.1 In order to continually innovate and improve the SDK, Google may collect certain usage statistics from the software including but not limited to a unique identifier, associated IP address, version number of the software, and information on which tools and/or services in the SDK are being used and how they are being used. Before any of this information is collected, the SDK will notify you and seek your consent. If you withhold consent, the information will not be collected.
2026-10-21T13:55:09.9288900Z 
2026-10-21T13:55:09.9289232Z 6.2 The data collected is examined in the aggregate to improve the SDK and is maintained in accordance with Google's Privacy Policy, which is located at the following URL: https://policies.google.com/privacy
2026-10-21T13:55:09.9289602Z 
2026-10-21T13:55:09.9289800Z 6.3 Anonymized and aggregated sets of the data may be shared with Google partners to improve the SDK. 7. Third Party Applications
2026-10-21T13:55:09.9290049Z 
2026-10-21T13:55:09.9290997Z 7.1 If you use the SDK to run applications developed by a third party or that access data, content or resources provided by a third party, you agree that Google is not responsible for those applications, data, content, or resources. You understand that all data, content or resources which you may access through such third party applications are the sole responsibility of the person from which they originated and that Google is not liable for any loss or damage that you may experience as a result of the use or access of any of those third party applications, data, content, or resources.
2026-10-21T13:55:09.9291986Z 
2026-10-21T13:55:09.9292773Z 7.2 You should be aware that the data, content, and resources presented to you through such a third party application may be protected by intellectual property rights which are owned by the providers (or by other persons or companies on their behalf). You may not modify, rent, lease, loan, sell, distribute or create derivative works based on these data, content, or resources (either in whole or in part) unless you have been specifically given permission to do so by the relevant owners.
2026-10-21T13:55:09.9293678Z 
2026-10-21T13:55:09.9294140Z 7.3 You acknowledge that your use of such third party applications, data, content, or resources may be subject to separate terms between you and the relevant third party. In that case, the License Agreement does not affect your legal relationship with these third parties. 8. Using Android APIs
2026-10-21T13:55:09.9294651Z 
2026-10-21T13:55:09.9294699Z 8.1 Google Data APIs
2026-10-21T13:55:09.9294777Z 
2026-10-21T13:55:09.9295569Z 8.1.1 If you use any API to retrieve data from Google, you acknowledge that the data may be protected by intellectual property rights which are owned by Google or those parties that provide the data (or by other persons or companies on their behalf). Your use of any such API may be subject to additional Terms of Service. You may not modify, rent, lease, loan, sell, distribute or create derivative works based on this data (either in whole or in part) unless allowed by the relevant Terms of Service.
2026-10-21T13:55:09.9296401Z 
2026-10-21T13:55:09.9297972Z 8.1.2 If you use any API to retrieve a user's data from Google, you acknowledge and agree that you shall retrieve data only with the user's explicit consent and only when, and for the limited purposes for which, the user has given you permission to do so. If you use the Android Recognition Service API, documented at the following URL: https://developer.android.com/reference/android/speech/RecognitionService, as updated from time to time, you acknowledge that the use of the API is subject to the Data Processing Addendum for Products where Google is a Data Processor, which is located at the following URL: https://privacy.google.com/businesses/gdprprocessorterms/, as updated from time to time. By clicking to accept, you hereby agree to the terms of the Data Processing Addendum for Products where Google is a Data Processor.
2026-10-21T13:55:09.9299362Z 
2026-10-21T13:55:09.9299424Z 9. Terminating this License Agreement
2026-10-21T13:55:09.9299606Z 
2026-10-21T13:55:09.9299769Z 9.1 The License Agreement will continue to apply until terminated by either you or Google as set out below.
2026-10-21T13:55:09.9299979Z 
2026-10-21T13:55:09.9300173Z 9.2 If you want to terminate the License Agreement, you may do so by ceasing your use of the SDK and any relevant developer credentials.
2026-10-21T13:55:09.9300419Z 
2026-10-21T13:55:09.9301442Z 9.3 Google may at any time, terminate the License Agreement with you if: (A) you have breached any provision of the License Agreement; or (B) Google is required to do so by law; or (C) the partner with whom Google offered certain parts of SDK (such as APIs) to you has terminated its relationship with Google or ceased to offer certain parts of the SDK to you; or (D) Google decides to no longer provide the SDK or certain parts of the SDK to users in the country in which you are resident or from which you use the service, or the provision of the SDK or certain SDK services to you by Google is, in Google's sole discretion, no longer commercially viable.
2026-10-21T13:55:09.9302506Z 
2026-10-21T13:55:09.9303220Z 9.4 When the License Agreement comes to an end, all of the legal rights, obligations and liabilities that you and Google have benefited from, been subject to (or which have accrued over time whilst the License Agreement has been in force) or which are expressed to continue indefinitely, shall be unaffected by this cessation, and the provisions of paragraph 14.7 shall continue to apply to such rights, obligations and liabilities indefinitely.
2026-10-21T13:55:09.9303972Z 
2026-10-21T13:55:09.9304025Z 10. DISCLAIMER OF WARRANTIES
2026-10-21T13:55:09.9304112Z 
2026-10-21T13:55:09.9304383Z 10.1 YOU EXPRESSLY UNDERSTAND AND AGREE THAT YOUR USE OF THE SDK IS AT YOUR SOLE RISK AND THAT THE SDK IS PROVIDED "AS IS" AND "AS AVAILABLE" WITHOUT WARRANTY OF ANY KIND FROM GOOGLE.
2026-10-21T13:55:09.9304698Z 
2026-10-21T13:55:09.9305188Z 10.2 YOUR USE OF THE SDK AND ANY MATERIAL DOWNLOADED OR OTHERWISE OBTAINED THROUGH THE USE OF THE SDK IS AT YOUR OWN DISCRETION AND RISK AND YOU ARE SOLELY RESPONSIBLE FOR ANY DAMAGE TO YOUR COMPUTER SYSTEM OR OTHER DEVICE OR LOSS OF DATA THAT RESULTS FROM SUCH USE.
2026-10-21T13:55:09.9305647Z 
2026-10-21T13:55:09.9306100Z 10.3 GOOGLE FURTHER EXPRESSLY DISCLAIMS ALL WARRANTIES AND CONDITIONS OF ANY KIND, WHETHER EXPRESS OR IMPLIED, INCLUDING, BUT NOT LIMITED TO THE IMPLIED WARRANTIES AND CONDITIONS OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. 11. LIMITATION OF LIABILITY
2026-10-21T13:55:09.9306603Z 
2026-10-21T13:55:09.9307441Z 11.1 YOU EXPRESSLY UNDERSTAND AND AGREE THAT GOOGLE, ITS SUBSIDIARIES AND AFFILIATES, AND ITS LICENSORS SHALL NOT BE LIABLE TO YOU UNDER ANY THEORY OF LIABILITY FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, CONSEQUENTIAL OR EXEMPLARY DAMAGES THAT MAY BE INCURRED BY YOU, INCLUDING ANY LOSS OF DATA, WHETHER OR NOT GOOGLE OR ITS REPRESENTATIVES HAVE BEEN ADVISED OF OR SHOULD HAVE BEEN AWARE OF THE POSSIBILITY OF ANY SUCH LOSSES ARISING.
2026-10-21T13:55:09.9308200Z 
2026-10-21T13:55:09.9308251Z 12. Indemnification
2026-10-21T13:55:09.9308321Z 
2026-10-21T13:55:09.9309491Z 12.1 To the maximum extent permitted by law, you agree to defend, indemnify and hold harmless Google, its affiliates and their respective directors, officers, employees and agents from and against any and all claims, actions, suits or proceedings, as well as any and all losses, liabilities, damages, costs and expenses (including reasonable attorneys fees) arising out of or accruing from (a) your use of the SDK, (b) any application you develop on the SDK that infringes any copyright, trademark, trade secret, trade dress, patent or other intellectual property right of any person or defames any person or violates their rights of publicity or privacy, and (c) any non-compliance by you with the License Agreement.
2026-10-21T13:55:09.9310700Z 
2026-10-21T13:55:09.9310758Z 13. Changes to the License Agreement
2026-10-21T13:55:09.9310857Z 
2026-10-21T13:55:09.9311229Z 13.1 Google may make changes to the License Agreement as it distributes new versions of the SDK. When these changes are made, Google will make a new version of the License Agreement available on the website where the SDK is made available.
2026-10-21T13:55:09.9311760Z 
2026-10-21T13:55:09.9311808Z 14. General Legal Terms
2026-10-21T13:55:09.9311886Z 
2026-10-21T13:55:09.9312371Z 14.1 The License Agreement constitutes the whole legal agreement between you and Google and governs your use of the SDK (excluding any services which Google may provide to you under a separate written agreement), and completely replaces any prior agreements between you and Google in relation to the SDK.
2026-10-21T13:55:09.9312904Z 
2026-10-21T13:55:09.9313405Z 14.2 You agree that if Google does not exercise or enforce any legal right or remedy which is contained in the License Agreement (or which Google has the benefit of under any applicable law), this will not be taken to be a formal waiver of Google's rights and that those rights or remedies will still be available to Google.
2026-10-21T13:55:09.9313955Z 
2026-10-21T13:55:09.9314501Z 14.3 If any court of law, having the jurisdiction to decide on this matter, rules that any provision of the License Agreement is invalid, then that provision will be removed from the License Agreement without affecting the rest of the License Agreement. The remaining provisions of the License Agreement will continue to be valid and enforceable.
2026-10-21T13:55:09.9315093Z 
2026-10-21T13:55:09.9315803Z 14.4 You acknowledge and agree that each member of the group of companies of which Google is the parent shall be third party beneficiaries to the License Agreement and that such other companies shall be entitled to directly enforce, and rely upon, any provision of the License Agreement that confers a benefit on (or rights in favor of) them. Other than this, no other person or company shall be third party beneficiaries to the License Agreement.
2026-10-21T13:55:09.9316643Z 
2026-10-21T13:55:09.9317129Z 14.5 EXPORT RESTRICTIONS. THE SDK IS SUBJECT TO UNITED STATES EXPORT LAWS AND REGULATIONS. YOU MUST COMPLY WITH ALL DOMESTIC AND INTERNATIONAL EXPORT LAWS AND REGULATIONS THAT APPLY TO THE SDK. THESE LAWS INCLUDE RESTRICTIONS ON DESTINATIONS, END USERS AND END USE.
2026-10-21T13:55:09.9317593Z 
2026-10-21T13:55:09.9318144Z 14.6 The rights granted in the License Agreement may not be assigned or transferred by either you or Google without the prior written approval of the other party. Neither you nor Google shall be permitted to delegate their responsibilities or obligations under the License Agreement without the prior written approval of the other party.
2026-10-21T13:55:09.9318734Z 
2026-10-21T13:55:09.9319639Z 14.7 The License Agreement, and your relationship with Google under the License Agreement, shall be governed by the laws of the State of California without regard to its conflict of laws provisions. You and Google agree to submit to the exclusive jurisdiction of the courts located within the county of Santa Clara, California to resolve any legal matter arising from the License Agreement. Notwithstanding this, you agree that Google shall still be allowed to apply for injunctive remedies (or an equivalent type of urgent legal relief) in any jurisdiction.
2026-10-21T13:55:09.9320642Z ---------------------------------------
2026-10-21T13:55:09.9320791Z Accept? (y/N): 
2026-10-21T13:55:09.9320927Z 7/7: License mips-android-sysimage-license:
2026-10-21T13:55:09.9321092Z ---------------------------------------
2026-10-21T13:55:09.9322986Z MIPS Technologies, Inc. (“MIPS”) Internal Evaluation License Agreement for MIPS Android™ System Images for Android Software Development Kit (SDK): This Internal Evaluation License Agreement (this "Agreement") is entered into by and between MIPS and you (as an individual developer or a legal entity -- identified below as “Recipient”). MIPS shall make the Evaluation Software available to Recipient as described in accordance with the terms and conditions set forth below.
2026-10-21T13:55:09.9323823Z 
2026-10-21T13:55:09.9324968Z By clicking on the “Accept” button, downloading, installing, or otherwise using the Evaluation Materials (defined below), you agree to be bound by the terms of this Agreement effective as of the date you click “Accept” (the “Effective Date”), and if doing so on behalf of an entity, you represent that you are authorized to bind the entity to the terms and conditions of this Agreement. If you do not agree to be bound by the terms and conditions of this Agreement, do not download, install, or use the Evaluation Materials.
2026-10-21T13:55:09.9325839Z 
2026-10-21T13:55:09.9325934Z 1. DEFINITIONS. These terms shall have the following meanings:
2026-10-21T13:55:09.9326077Z 
2026-10-21T13:55:09.9326393Z 1.1 “MIPS” shall mean MIPS Technologies, Inc., a Delaware corporation having a principal place of business at: 955 East Arques Ave., Sunnyvale, CA 94085
2026-10-21T13:55:09.9326682Z 
2026-10-21T13:55:09.9327162Z 1.2 “Evaluation Software” shall mean MIPS Android™ emulator system images for Android Software Development Kit (SDK), as made available to Recipient.
2026-10-21T13:55:09.9327460Z 
2026-10-21T13:55:09.9328081Z 1.3 “Evaluation Materials" means, collectively, the Evaluation Software (in source and/or object code form) and documentation (including, without limitation, any design documents, specifications, reference manuals, and other related materials) related to the Evaluation Software as made available to Recipient.
2026-10-21T13:55:09.9328637Z 
2026-10-21T13:55:09.9330584Z 1.4 “Open Source Software” means any software that requires (as a condition of use, modification and/or distribution of such software) that such software or other software incorporated into, derived from or distributed with such software (a) be disclosed or distributed in source code form; or (b) be licensed by the user to third parties for the purpose of making and/or distributing derivative works; or (c) be redistributable at no charge. Open Source Software includes, without limitation, software licensed or distributed under any of the following licenses or distribution models, or licenses or distribution models substantially similar to any of the following: (a) GNU’s General Public License (GPL) or Lesser/Library GPL (LGPL), (b) the Artistic License (e.g., PERL), (c) the Mozilla Public License, (d) the Netscape Public License, (e) the Sun Community Source License (SCSL), (f) the Sun Industry Source License (SISL), (g) the Apache Software license and (h) the Common Public License (CPL).
2026-10-21T13:55:09.9332352Z 
2026-10-21T13:55:09.9332942Z 1.5 “Pre-Release Materials” means “alpha” or “beta” designated pre-release features, which may not be fully functional, which MIPS may substantially modify in producing any production version of the Evaluation Materials, and/or which is still under development by MIPS and/or MIPS’ suppliers.
2026-10-21T13:55:09.9333476Z 
2026-10-21T13:55:09.9334424Z 2. PURPOSE. MIPS desires to make the Evaluation Materials available to Recipient solely for Recipient's internal evaluation of the Evaluation Software to evaluate the desirability of cooperating with MIPS in developing products that are compatible with the Evaluation Software and/or to advise MIPS as to possible modifications to the Evaluation Software. Recipient may not disclose, distribute, modify (except to facilitate the above-mentioned internal evaluation), or make commercial use of the Evaluation Materials or any modifications of the Evaluation Materials.
2026-10-21T13:55:09.9335413Z 
2026-10-21T13:55:09.9336121Z THE EVALUATION MATERIALS ARE PROVIDED FOR EVALUATION PURPOSES ONLY AND MAY NOT BE MODIFIED (EXCEPT TO FACILITATE THE INTERNAL EVALUATION) OR DISTRIBUTED BY RECIPIENT OR INCORPORATED INTO RECIPIENT’S PRODUCTS OR SOFTWARE. PLEASE CONTACT A MIPS SALES REPRESENTATIVE TO LEARN ABOUT THE AVAILABILITY AND COST OF A COMMERCIAL VERSION OF THE EVALUATION SOFTWARE.
2026-10-21T13:55:09.9336752Z 
2026-10-21T13:55:09.9337344Z 3. TITLE. Title to the Evaluation Materials remains with MIPS or its suppliers. Recipient shall not mortgage, pledge or encumber the Evaluation Materials in any way. Recipient shall return all Evaluation Materials, keeping no copies, upon termination or expiration of this Agreement.
2026-10-21T13:55:09.9337936Z 
2026-10-21T13:55:09.9339856Z 4. LICENSE. MIPS grants Recipient a royalty-free, personal, nontransferable, nonexclusive license under its copyrights to use the Evaluation Software only for the purposes described in paragraph 2 above and only for a period beginning on the Effective Date and extending to the first anniversary of the Effective Date (the “Evaluation Period”). Unless otherwise communicated in writing by MIPS to Recipient, to the extent the Evaluation Software is provided in more than one delivery or release (each, a “Release”) the license grant in this Section 4 and the Evaluation Period shall apply to each Release, in which case the Evaluation Period shall begin on the date that the Release is made generally available and continue to the first anniversary of such date. Recipient may not make modifications to the Evaluation Software. Recipient shall not disassemble, reverse-engineer, or decompile any software that is not provided to Recipient in source code form.
2026-10-21T13:55:09.9341485Z 
2026-10-21T13:55:09.9341488Z 
2026-10-21T13:55:09.9342037Z EXCEPT AS PROVIDED HEREIN, NO OTHER LICENSE, EXPRESS OR IMPLIED, BY ESTOPPEL OR OTHERWISE, TO ANY OTHER MIPS INTELLECTUAL PROPERTY RIGHTS IS GRANTED TO THE RECIPIENT. OTHER THAN AS EXPLICITLY SET FORTH IN PARAGRAPH 2 ABOVE, NO RIGHT TO COPY, TO REPRODUCE, TO MODIFY, OR TO CREATE DERIVATIVE WORKS OF, THE EVALUATION MATERIALS IS GRANTED HEREIN.
2026-10-21T13:55:09.9342628Z 
2026-10-21T13:55:09.9343042Z 5. NO OBLIGATION. Recipient shall have no duty to purchase or license any product from MIPS. MIPS and its suppliers shall have no obligation to provide support for, or develop a non-evaluation version of, the Evaluation Software or to license any version of it.
2026-10-21T13:55:09.9343643Z 
2026-10-21T13:55:09.9346506Z 6. MODIFICATIONS. This Agreement does not obligate Recipient to provide MIPS with comments or suggestions regarding Evaluation Materials. However, should Recipient provide MIPS with comments or suggestions for the modification, correction, improvement or enhancement of (a) the Evaluation Materials or (b) MIPS products or processes which may embody the Evaluation Materials, then Recipient agrees to grant and hereby grants to MIPS a non-exclusive, irrevocable, worldwide, fully paid-up, royalty-free license, with the right to sublicense MIPS’ licensees and customers, under Recipient’s Intellectual property rights, to use and disclose such comments and suggestions in any manner MIPS chooses and to display, perform, copy, make, have made, use, sell, offer to sell, import, and otherwise dispose of MIPS’ and its sublicensee’s products embodying such comments and suggestions in any manner and via any media MIPS chooses, without reference to the source.
2026-10-21T13:55:09.9349513Z 
2026-10-21T13:55:09.9351068Z 7. WARRANTY DISCLAIMER. MIPS AND ITS SUPPLIERS MAKE NO WARRANTIES WITH RESPECT TO EVALUATION MATERIALS, EITHER EXPRESS OR IMPLIED, INCLUDING ANY IMPLIED WARRANTIES OF MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE, OR ANY IMPLIED WARRANTY OF NONINFRINGEMENT WITH RESPECT TO THIRD PARTY INTELLECTUAL PROPERTY. RECIPIENT ACKNOWLEDGES AND AGREES THAT THE EVALUATION MATERIALS ARE PROVIDED “AS IS,” WITHOUT WARRANTY OF ANY KIND.
2026-10-21T13:55:09.9352397Z 
2026-10-21T13:55:09.9354124Z 8. LIMITATION OF LIABILITY. MIPS AND ITS SUPPLIERS SHALL NOT BE LIABLE FOR ANY PROPERTY DAMAGE, PERSONAL INJURY, LOSS OF PROFITS, INTERRUPTION OF BUSINESS OR FOR ANY DIRECT, INDIRECT, SPECIAL, CONSEQUENTIAL OR INCIDENTAL DAMAGES, HOWEVER CAUSED OR ALLEGED, WHETHER FOR BREACH OF WARRANTY, CONTRACT, STRICT LIABILITY OR OTHERWISE, INCLUDING WITHOUT LIMITATION, UNDER TORT OR OTHER LEGAL THEORY. MIPS AND ITS SUPPLIERS DISCLAIM ANY AND ALL LIABILITY, INCLUDING LIABILITY FOR INFRINGEMENT OF ANY INTELLECTUAL PROPERTY RIGHTS OF ANY KIND RELATING TO THE EVALUATION MATERIALS.
2026-10-21T13:55:09.9355942Z 
2026-10-21T13:55:09.9356591Z 9. EXPIRATION. MIPS may terminate this Agreement immediately after a breach by Recipient or otherwise at MIPS’ reasonable discretion and upon five (5) business days’ notice to Recipient.
2026-10-21T13:55:09.9357291Z 
2026-10-21T13:55:09.9357368Z 10. GENERAL.
2026-10-21T13:55:09.9357470Z 
2026-10-21T13:55:09.9360292Z 10.1 Controlling Law. This Agreement shall be governed by California law excluding its choice of law rules. With the exception of MIPS’ rights to enforce its intellectual property rights and any confidentiality obligations under this Agreement or any licenses distributed with the Evaluation Materials, all disputes and any claims arising under or relating to this Agreement shall be subject to the exclusive jurisdiction and venue of the state and federal courts located in Santa Clara County, California. Each party hereby agrees to jurisdiction and venue in the courts set forth in the preceding sentence. The parties agree that the United Nations Convention on Contracts for the International Sale of Goods is specifically excluded from application to this Agreement. The parties consent to the personal jurisdiction of the above courts.
2026-10-21T13:55:09.9361943Z 
2026-10-21T13:55:09.9362753Z 10.2 Remedies. Recipient acknowledges and agrees that any breach of confidentiality obligations under this Agreement or any licenses distributed with the Evaluation Materials, as well as any disclosure, commercialization, or public use of the Evaluation Materials, would cause irreparable injury to MIPS, and therefore Recipient agrees to consent to, and hereby consents to, the grant of an injunction by any court of competent jurisdiction in the event of an actual or threatened breach.
2026-10-21T13:55:09.9363591Z 
2026-10-21T13:55:09.9364654Z 10.3 Assignment. Recipient may not delegate, assign or transfer this Agreement, the license granted or any of Recipient’s rights, obligations, or duties hereunder, expressly, by implication, by operation of law, by way of merger (regardless of whether Recipient is the surviving entity) or acquisition, or otherwise and any attempt to do so, without MIPS’ express prior written consent, shall be ineffective, null and void. MIPS may freely assign this Agreement, and its rights and obligations hereunder, in its sole discretion.
2026-10-21T13:55:09.9365694Z 
2026-10-21T13:55:09.9366458Z 10.4 Entire Agreement. This Agreement constitutes the entire agreement between Recipient and MIPS and supersedes in their entirety any and all oral or written agreements previously existing between Recipient and MIPS with respect to the subject matter hereof. This Agreement may only be amended or supplemented by a writing that refers explicitly to this Agreement and that is signed or otherwise accepted by duly authorized representatives of Recipient and MIPS.
2026-10-21T13:55:09.9367408Z 
2026-10-21T13:55:09.9368248Z 10.5 Severability. In the event that any provision of this Agreement is finally adjudicated to be unenforceable or invalid under any applicable law, such unenforceability or invalidity shall not render this Agreement unenforceable or invalid as a whole, and, in such event, such unenforceable or invalid provision shall be interpreted so as to best accomplish the objectives of such provision within the limits of applicable law or applicable court decisions.
2026-10-21T13:55:09.9369049Z 
2026-10-21T13:55:09.9371653Z 10.6 Export Regulations / Export Control. Recipient shall not export, either directly or indirectly, any product, service or technical data or system incorporating the Evaluation Materials without first obtaining any required license or other necessary approval from the U.S. Department of Commerce or any other governing agency or department of the United States Government. In the event any product is exported from the United States or re-exported from a foreign destination by Recipient, Recipient shall ensure that the distribution and export/re-export or import of the product is in compliance with all applicable laws, regulations, orders, or other restrictions of the U.S. Export Administration Regulations and the appropriate foreign government. Recipient agrees that neither it nor any of its subsidiaries will export/re-export any technical data, process, product, or service, directly or indirectly, to any country for which the United States government or any agency thereof or the foreign government from where it is shipping requires an export license, or other governmental approval, without first obtaining such license or approval. Recipient also agrees to implement measures to ensure that foreign national employees are authorized to receive any information controlled by U.S. export control laws. An export is "deemed" to take place when information is released to a foreign national wherever located.
2026-10-21T13:55:09.9374267Z 
2026-10-21T13:55:09.9376127Z 10.7 Special Terms for Pre-Release Materials. If so indicated in the description of the Evaluation Software, the Evaluation Software may contain Pre-Release Materials. Recipient hereby understands, acknowledges and agrees that: (i) Pre-Release Materials may not be fully tested and may contain bugs or errors; (ii) Pre-Release materials are not suitable for commercial release in their current state; (iii) regulatory approvals for Pre-Release Materials (such as UL or FCC) have not been obtained, and Pre-Release Materials may therefore not be certified for use in certain countries or environments or may not be suitable for certain applications and (iv) MIPS can provide no assurance that it will ever produce or make generally available a production version of the Pre-Release Materials . MIPS is not under any obligation to develop and/or release or offer for sale or license a final product based upon the Pre-Release Materials and may unilaterally elect to abandon the Pre-Release Materials or any such development platform at any time and without any obligation or liability whatsoever to Recipient or any other person.
2026-10-21T13:55:09.9378382Z 
2026-10-21T13:55:09.9378756Z ANY PRE-RELEASE MATERIALS ARE NON-QUALIFIED AND, AS SUCH, ARE PROVIDED “AS IS” AND “AS AVAILABLE”, POSSIBLY WITH FAULTS, AND WITHOUT REPRESENTATION OR WARRANTY OF ANY KIND.
2026-10-21T13:55:09.9379077Z 
2026-10-21T13:55:09.9380131Z 10.8 Open Source Software. In the event Open Source software is included with Evaluation Software, such Open Source software is licensed pursuant to the applicable Open Source software license agreement identified in the Open Source software comments in the applicable source code file(s) and/or file header as indicated in the Evaluation Software. Additional detail may be available (where applicable) in the accompanying on-line documentation. With respect to the Open Source software, nothing in this Agreement limits any rights under, or grants rights that supersede, the terms of any applicable Open Source software license agreement.
2026-10-21T13:55:09.9381269Z ---------------------------------------
2026-10-21T13:55:09.9381456Z Accept? (y/N): All SDK package licenses accepted
2026-10-21T13:55:09.9381574Z 
2026-10-21T13:55:09.9381732Z [command]/usr/local/lib/android/sdk/cmdline-tools/16.0/bin/sdkmanager tools
2026-10-21T13:55:10.4679912Z Loading package information...                                                  
2026-10-21T13:55:10.5365984Z Loading local repository...                                                     
2026-10-21T13:55:10.5369101Z [                                       ] 3% Loading local repository...        
2026-10-21T13:55:10.5441930Z [                                       ] 3% Fetch remote repository...         
2026-10-21T13:55:10.6845796Z [=                                      ] 3% Fetch remote repository...         
2026-10-21T13:55:10.7383916Z [=                                      ] 4% Fetch remote repository...         
2026-10-21T13:55:10.7533991Z [=                                      ] 5% Fetch remote repository...         
2026-10-21T13:55:10.7771346Z [==                                     ] 5% Fetch remote repository...         
2026-10-21T13:55:10.8209967Z [==                                     ] 6% Fetch remote repository...         
2026-10-21T13:55:10.8701930Z [==                                     ] 7% Fetch remote repository...         
2026-10-21T13:55:10.8727720Z [==                                     ] 7% Computing updates...               
2026-10-21T13:55:10.8787618Z [===                                    ] 8% Computing updates...               
2026-10-21T13:55:10.8793804Z [===                                    ] 10% Computing updates...              
2026-10-21T13:55:10.8794170Z                                                                                 
2026-10-21T13:55:10.8794659Z Warning: Failed to find package 'tools'
2026-10-21T13:55:11.0635484Z /home/runner/work/_actions/android-actions/setup-android/v3/dist/index.js:1823
2026-10-21T13:55:11.0636045Z                 error = new Error(`The process '${this.toolPath}' failed with exit code ${this.processExitCode}`);
2026-10-21T13:55:11.0636347Z                         ^
2026-10-21T13:55:11.0636440Z 
2026-10-21T13:55:11.0636653Z Error: The process '/usr/local/lib/android/sdk/cmdline-tools/16.0/bin/sdkmanager' failed with exit code 1
2026-10-21T13:55:11.0637313Z     at ExecState._setResult (/home/runner/work/_actions/android-actions/setup-android/v3/dist/index.js:1823:25)
2026-10-21T13:55:11.0637937Z     at ExecState.CheckComplete (/home/runner/work/_actions/android-actions/setup-android/v3/dist/index.js:1806:18)
2026-10-21T13:55:11.0638613Z     at ChildProcess.<anonymous> (/home/runner/work/_actions/android-actions/setup-android/v3/dist/index.js:1700:27)
2026-10-21T13:55:11.0639103Z     at ChildProcess.emit (node:events:514:28)
2026-10-21T13:55:11.0639408Z     at maybeClose (node:internal/child_process:1141:16)
2026-10-21T13:55:11.0639696Z     at ChildProcess._handle.onexit (node:internal/child_process:306:5)
2026-10-21T13:55:11.0639845Z 
2026-10-21T13:55:11.0639895Z Node.js v24.21.0
2026-10-21T13:55:11.0748009Z [===                                    ] 10% Computing updates...              
